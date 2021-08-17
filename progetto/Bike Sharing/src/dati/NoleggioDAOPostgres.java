package dati;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import dominio.Abbonamento;
import dominio.Bicicletta;
import dominio.Noleggio;
import dominio.TipoBicicletta;
import dominio.Totem;

public class NoleggioDAOPostgres implements NoleggioDAO {
	private ConnessioneDb connessioneDb;
	
	public NoleggioDAOPostgres() {
		this.connessioneDb = ConnessioneDb.getIstance();
	}
	
	@Override
	public List<Noleggio> getListaNoleggi(Abbonamento abbonamento) throws NoSuchElementException {
		System.out.println("Prelevo la lista di noleggi dell'abbonamento con id: " + abbonamento.getCodice());
		Connection connessione = this.connessioneDb.getConnessione();
		
		List<Noleggio> noleggi = new ArrayList<Noleggio>();
		
        try {
			PreparedStatement statement = connessione.prepareStatement("SELECT n.id, n.orarioinizionoleggio, n.orariofinenoleggio, t.id, t.indirizzo, b.id, b.tipo, b.danneggiata "
					+ "FROM noleggio AS n JOIN bicicletta AS b ON b.id = n.bicicletta JOIN totem AS t ON t.id = n.totem "
					+ "WHERE abbonamento = ?");
			statement.setString(1, abbonamento.getCodice());
			
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Noleggio noleggio = new Noleggio(
						resultSet.getString(1),
						abbonamento,
						new Bicicletta(resultSet.getString(6), TipoBicicletta.valueOf(resultSet.getString(7)), resultSet.getBoolean(8)),
						new Totem(resultSet.getString(4), resultSet.getString(5)),
						resultSet.getTimestamp(2),
						resultSet.getTimestamp(3)
				);
				noleggi.add(noleggio);
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        if (noleggi.isEmpty()) throw new NoSuchElementException("Non ci sono noleggi riguardo questo abbonamento al momento.");
        
        return noleggi;
	}
	
	@Override
	public void iniziaNoleggio(Abbonamento abbonamento, Totem totem, TipoBicicletta tipoBicicletta) throws IllegalStateException {
		System.out.println("Aggiungo un nuovo noleggio legato all'abbonamento con codice: " + abbonamento.getCodice());
		Connection connessione = this.connessioneDb.getConnessione();
		BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
		
		if (this.hasNoleggioInCorso(abbonamento)) throw new IllegalStateException("Impossibile creare l'abbonamento: c'è n'è già uno in corso.");
		if (!this.passatiCinqueMinuti(abbonamento)) throw new IllegalStateException("Impossibile creare l'abbonamento: non sono ancora passati 5 minuti dall'ultimo effettuato.");
		
		try {
			Bicicletta bicicletta = biciclettaDao.noleggiaBicicletta(totem, tipoBicicletta);
			Noleggio noleggio = new Noleggio(abbonamento, bicicletta, totem);
			PreparedStatement statement = connessione.prepareStatement("INSERT INTO noleggio VALUES(?,?,?,?,?,?)");
        	statement.setString(1, noleggio.getId());
        	statement.setTimestamp(2, noleggio.getOrarioInizioNoleggio());
        	statement.setNull(3, java.sql.Types.NULL); // Noleggio appena registrato => orario fine noleggio = null
        	statement.setString(4, noleggio.getBicicletta().getId());
        	statement.setString(5, noleggio.getAbbonamento().getCodice());
        	statement.setString(6, noleggio.getTotem().getId());
        	
        	statement.executeUpdate();
        	statement.close();
		} catch (SQLException e) {
			if(e.getSQLState().equals("23505")) throw new IllegalStateException("Il noleggio è già presente nel sistema, impossibile proseguire.");
			else e.printStackTrace();
		}
	}

	@Override
	public void finisciNoleggio(Abbonamento abbonamento, Totem totem) throws IllegalStateException {
		System.out.println("Registro la fine del noleggio legato all'abbonamento con codice: " + abbonamento.getCodice());
		Connection connessione = this.connessioneDb.getConnessione();
		BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
		
		Noleggio noleggioInCorso = this.getNoleggioInCorso(abbonamento);
		noleggioInCorso.terminaNoleggio();
		int noleggiAggiornati = 0;
		
		try {
			biciclettaDao.restituisciBicicletta(totem, noleggioInCorso.getBicicletta());
			PreparedStatement statement = connessione.prepareStatement("UPDATE noleggio SET orariofinenoleggio = ? WHERE abbonamento = ? AND orariofinenoleggio IS NULL");
        	statement.setTimestamp(1, noleggioInCorso.getOrarioFineNoleggio());
        	statement.setString(2, noleggioInCorso.getAbbonamento().getCodice());
        	
        	noleggiAggiornati = statement.executeUpdate();
        	statement.close();
		} catch (IllegalStateException e) {
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (noleggiAggiornati > 1) throw new IllegalStateException("Errore di fine noleggio: l'abbonamento " + abbonamento.getCodice() + " ha più di un noleggio in corso.");
		else if (noleggiAggiornati < 1) throw new IllegalStateException("Errore di fine noleggio: l'abbonamento " + abbonamento.getCodice() + " non ha noleggi in corso.");
		
		/* Eventuale ammonizione all'abbonamento */
		long durataNoleggio = noleggioInCorso.getDurataNoleggio();
		AbbonamentoDAOPostgres abbonamentoDao = new AbbonamentoDAOPostgres();
		
		if (durataNoleggio > 120) abbonamentoDao.ammonisciAbbonamento(abbonamento); // Qui viene gestita anche l'eventuale sospensione
	}
	
	@Override
	public Noleggio getNoleggioInCorso(Abbonamento abbonamento) throws NoSuchElementException {
		System.out.println("Calcolo se c'è un noleggio in corso legato all'abbonamento con codice: " + abbonamento.getCodice());
		Connection connessione = this.connessioneDb.getConnessione();
		Noleggio noleggio = null;
		
		try {
			PreparedStatement statement = connessione.prepareStatement("SELECT n.id, n.orarioinizionoleggio, n.orariofinenoleggio, t.id, t.indirizzo, b.id, b.tipo, b.danneggiata "
					+ "FROM noleggio AS n JOIN bicicletta AS b ON b.id = n.bicicletta JOIN totem AS t ON t.id = n.totem "
					+ "WHERE abbonamento = ? AND orariofinenoleggio IS NULL");
			statement.setString(1, abbonamento.getCodice());
			
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				noleggio = new Noleggio(
						resultSet.getString(1),
						abbonamento,
						new Bicicletta(resultSet.getString(6), TipoBicicletta.valueOf(resultSet.getString(7)), resultSet.getBoolean(8)),
						new Totem(resultSet.getString(4), resultSet.getString(5)),
						resultSet.getTimestamp(2),
						resultSet.getTimestamp(3)
				);
			} else throw new NoSuchElementException("Non c'è un noleggio in corso attualmente per l'abbonamento " + abbonamento.getCodice());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return noleggio;
	}
	
	@Override
	public boolean hasNoleggioInCorso(Abbonamento abbonamento) {
		try {
			Noleggio noleggio = this.getNoleggioInCorso(abbonamento);
			if (noleggio == null) return false;
		} catch (NoSuchElementException e) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean passatiCinqueMinuti(Abbonamento abbonamento) {
		System.out.println("Calcolo se ci sono stati noleggi negli ultimi cinque minuti legati all'abbonamento con codice: " + abbonamento.getCodice());
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			PreparedStatement statement = connessione.prepareStatement("SELECT * FROM noleggio WHERE abbonamento = ? AND orariofinenoleggio >= (NOW() - INTERVAL '5 minutes') AND orariofinenoleggio IS NOT NULL");
			statement.setString(1, abbonamento.getCodice());
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}