package dati;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import dominio.Bicicletta;
import dominio.Morsa;
import dominio.TipoBicicletta;
import dominio.Totem;

public class BiciclettaDAOPostgres implements BiciclettaDAO {
	private ConnessioneDb connessioneDb;
	
	public BiciclettaDAOPostgres() {
		this.connessioneDb = ConnessioneDb.getIstance();
	}
	
	@Override
	public Bicicletta noleggiaBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws NoSuchElementException {
		System.out.println("Noleggio di una bicicletta di tipo " + tipoBicicletta + " dalla postazione con totem avente id: " + totem.getId());
		Connection connessione = this.connessioneDb.getConnessione();
		Bicicletta bicicletta = null;
		
		try {
			bicicletta = totem.noleggiaBicicletta(tipoBicicletta);

			PreparedStatement statement = connessione.prepareStatement("UPDATE morsa SET bicicletta = NULL WHERE bicicletta = ?");
			statement.setString(1, bicicletta.getId());
			
			statement.executeUpdate();
			statement.close();
		} catch (NoSuchElementException e) {
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bicicletta;
	}

	@Override
	public void restituisciBicicletta(Totem totem, Bicicletta bicicletta) throws IllegalStateException {
		System.out.println("Restituisco una bicicletta di tipo " + bicicletta.getTipo() + " alla postazione con totem avente id: " + totem.getId());
		Connection connessione = this.connessioneDb.getConnessione();

		try {
			Morsa morsa = totem.restituisciBicicletta(bicicletta);
			
			PreparedStatement statement = connessione.prepareStatement("UPDATE morsa SET bicicletta = ? WHERE id = ?");
        	statement.setString(1, bicicletta.getId());
        	statement.setString(2, morsa.getId());
        	
        	statement.executeUpdate();
        	statement.close();
		} catch (IllegalStateException e) {
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void aggiungiBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws IllegalStateException {
		System.out.println("Aggiungo una bicicletta di tipo " + tipoBicicletta + " alla postazione con totem avente id: " + totem.getId());
		Connection connessione = this.connessioneDb.getConnessione();
		Bicicletta bicicletta = new Bicicletta(tipoBicicletta);
		
		try {
			Morsa morsaUtilizzata = totem.restituisciBicicletta(bicicletta);
			
			// Aggiungo prima la bicicletta nella tabella bicicletta, e poi aggiorno la morsa a cui viene associata
			PreparedStatement statement = connessione.prepareStatement("INSERT INTO bicicletta VALUES(?,?,?)");
			statement.setString(1, bicicletta.getId());
			statement.setString(2, bicicletta.getTipo().toString());
			statement.setBoolean(3, bicicletta.isDanneggiata());
			
        	statement.executeUpdate();
        	statement.close();
        	
        	// Ho aggiunto la bicicletta ma la morsa a cui è collegata nel db non risulta associata, bisogna aggiornare la chiave esterna
        	statement = connessione.prepareStatement("UPDATE morsa SET bicicletta = ? WHERE id = ?");
        	statement.setString(1, bicicletta.getId());
        	statement.setString(2, morsaUtilizzata.getId());
        	
        	statement.executeUpdate();
        	statement.close();
		} catch (IllegalStateException e) {
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void rimuoviBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws NoSuchElementException {
		// Qui devi controllare se c'è il tipo di bicicletta per rimuoverla
		System.out.println("Rimuovo una bicicletta di tipo " + tipoBicicletta + " dalla postazione con totem avente id: " + totem.getId());
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			// Questo ovviamente non è un noleggio, mi serve solamente per prendere la bici ma non risulterà tale
			Bicicletta bicicletta = totem.noleggiaBicicletta(tipoBicicletta);

			PreparedStatement statement = connessione.prepareStatement("UPDATE morsa SET bicicletta = NULL WHERE bicicletta = ?");
			statement.setString(1, bicicletta.getId());
			
			statement.executeUpdate();
			statement.close();
			
			statement = connessione.prepareStatement("DELETE FROM bicicletta WHERE id = ?");
			statement.setString(1, bicicletta.getId());
			
			statement.executeUpdate();
			statement.close();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Non ci sono biciclette di tipo " + tipoBicicletta + " da rimuovere in questa postazione con totem.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void comunicaDanni(Bicicletta bicicletta) {
		System.out.println("Segnalo danni sulla bicicletta con id " + bicicletta.getId());
		Connection connessione = this.connessioneDb.getConnessione();
		bicicletta.setDanneggiata();
		
		try {
			PreparedStatement statement = connessione.prepareStatement("UPDATE bicicletta SET danneggiata = true WHERE id = ?");
			statement.setString(1, bicicletta.getId());
			
			statement.executeUpdate();
			statement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Metodo utilizzabile solo dal personale di servizio */
	@Override
	public void riparaBicicletta(Bicicletta bicicletta) throws IllegalStateException {
		System.out.println("Riparo la bicicletta con id " + bicicletta.getId());
		Connection connessione = this.connessioneDb.getConnessione();
		
		if (!bicicletta.isDanneggiata()) throw new IllegalStateException("La bicicletta non è danneggiata quindi non può essere riparata.");
		
		bicicletta.riparaBicicletta();
		
		try {
			PreparedStatement statement = connessione.prepareStatement("UPDATE bicicletta SET danneggiata = false WHERE id = ?");
			statement.setString(1, bicicletta.getId());
			
			statement.executeUpdate();
			statement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getPosizioneNellaPostazione(Totem totem, Bicicletta bicicletta) throws IllegalStateException {
		System.out.println("Calcolo la posizione della bicicletta di tipo " + bicicletta.getTipo() + " che verrà noleggiata..");
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			PreparedStatement statement = connessione.prepareStatement("SELECT posizione FROM morsa WHERE bicicletta = ?");
			statement.setString(1, bicicletta.getId());
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) { return resultSet.getInt(1); }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		throw new NoSuchElementException("La bicicletta in questione non si trova in questa postazione con totem.");
	}
}