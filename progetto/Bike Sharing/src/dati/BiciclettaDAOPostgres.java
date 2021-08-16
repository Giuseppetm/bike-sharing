package dati;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
			throw new IllegalStateException("Non c'è spazio per aggiungere questa bicicletta nella postazione.");
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
	public void riparaBicicletta(Bicicletta bicicletta) {
		System.out.println("Riparo la bicicletta con id " + bicicletta.getId());
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			PreparedStatement statement = connessione.prepareStatement("UPDATE bicicletta SET danneggiata = false WHERE id = ?");
			statement.setString(1, bicicletta.getId());
			
			statement.executeUpdate();
			statement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}