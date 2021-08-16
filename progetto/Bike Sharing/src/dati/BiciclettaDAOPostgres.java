package dati;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        	
        	// Ho aggiunto la bicicletta ma la morsa a cui è collegata nel db non risulta associata, bisogna aggiornare la chiave esternaù
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
	public void rimuoviBicicletta(Totem totem, TipoBicicletta tipoBicicletta) {
		// Qui devi controllare se c'è il tipo di bicicletta per rimuoverla
		
	}
	
	@Override
	public void comunicaDanni(Bicicletta bicicletta) {
		// To-do: magari un operatore può anche riparare le bici? Nella gestione del sistema aggiungo qualcosa per la riparazione, sarebbe carino
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
}