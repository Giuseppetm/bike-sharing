package dati;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import dominio.Morsa;
import dominio.Totem;

public class TotemDAOPostgres implements TotemDAO {
	private ConnessioneDb connessioneDb;
	
	public TotemDAOPostgres() {
		this.connessioneDb = ConnessioneDb.getIstance();
	}
	
	@Override /* Avere tutti i riferimenti del totem potrebbe essere molto utile */
	public List<Totem> getListaTotem() throws NoSuchElementException {
		System.out.println("Prelevo lista dei totem..\n");
		List<Totem> totems = new ArrayList<Totem>();
		
		MorsaDAOPostgres morsaDao = new MorsaDAOPostgres();

		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
        	Statement statement = connessione.createStatement();
        	ResultSet resultSet = statement.executeQuery("SELECT id, indirizzo FROM totem");
        	
        	while (resultSet.next()) {
        		List<Morsa> morse = morsaDao.getMorse(new Totem(resultSet.getString(1), resultSet.getString(2)));
            	
            	Totem totem = new Totem(resultSet.getString(1), resultSet.getString(2), morse);
            	totems.add(totem);
        	}
    
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (totems.isEmpty()) throw new NoSuchElementException("Non ci sono ancora totem inizializzati.");
		return totems;
	}
	
	@Override
	public List<String> getListaTotemID() throws NoSuchElementException {
		System.out.println("Prelevo lista degli id dei totem..");
		Connection connessione = this.connessioneDb.getConnessione();
		List<String> totemIDList = new ArrayList<String>();
		
		try {
        	Statement statement = connessione.createStatement();
        	ResultSet resultSet = statement.executeQuery("SELECT id FROM totem");

        	while (resultSet.next())
        		totemIDList.add(resultSet.getString(1));     
        	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (totemIDList.isEmpty()) throw new NoSuchElementException("Non ci sono ancora totem inizializzati.");
		
		return totemIDList;
	}
	
	/* Questo metodo registra nel database un totem appena inizializzato e quindi senza morse e biciclette collegate. */
	@Override
	public void aggiungiTotem(Totem totem) {
		System.out.println("Aggiungo nuova postazione con totem; id: " + totem.getId() + ", indirizzo: " + totem.getIndirizzo());
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			PreparedStatement statement = connessione.prepareStatement("INSERT INTO totem VALUES(?,?)");
        	statement.setString(1, totem.getId());
        	statement.setString(2, totem.getIndirizzo());
        	
        	statement.executeUpdate();
        	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void rimuoviTotem(Totem totem) {
		System.out.println("Rimuovo postazione con totem; id: " + totem.getId() + ", indirizzo: " + totem.getIndirizzo());
		Connection connessione = this.connessioneDb.getConnessione();
		
		MorsaDAOPostgres morsaDao = new MorsaDAOPostgres();
		
		try {
			morsaDao.rimuoviMorse(totem);
			PreparedStatement statement = connessione.prepareStatement("DELETE FROM totem WHERE id = ?");
        	statement.setString(1, totem.getId());
        	
        	statement.executeUpdate();
        	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}