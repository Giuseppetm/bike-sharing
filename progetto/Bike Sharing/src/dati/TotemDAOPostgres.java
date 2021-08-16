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
import dominio.TipoBicicletta;
import dominio.TipoMorsa;
import dominio.Totem;

public class TotemDAOPostgres implements TotemDAO {
	private ConnessioneDb connessioneDb;
	
	public TotemDAOPostgres() {
		this.connessioneDb = ConnessioneDb.getIstance();
	}
	
	/* Avere tutti i riferimenti del totem qui è molto utile */
	@Override
	public List<Totem> getListaTotem() throws NoSuchElementException {
		System.out.println("Prelevo lista dei totem..");
		List<Totem> totems = new ArrayList<Totem>();
		List<Morsa> morse = new ArrayList<Morsa>();

		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
        	Statement statement = connessione.createStatement();
        	ResultSet resultSet = statement.executeQuery("SELECT T.id, T.indirizzo FROM totem AS T");
        	
        	// Qui posso prelevare la lista di morse con un metodo apposito che lavora sulla tabella morsa! Dove lo metti il metodo? Qui in totemdao va bene?
        	
        	Totem totem = new Totem(resultSet.getString(1), resultSet.getString(2), morse);
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
		// To-do: rimuovere tutte le morse e biciclette collegate al totem in questione
		System.out.println("Rimuovo postazione con totem; id: " + totem.getId() + ", indirizzo: " + totem.getIndirizzo());
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			PreparedStatement statement = connessione.prepareStatement("DELETE FROM totem WHERE id = ?");
        	statement.setString(1, totem.getId());
        	
        	statement.executeUpdate();
        	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void aggiungiMorsa(Totem totem, TipoMorsa tipoMorsa) {
		// Qui non credo ci siano problemi, aggiungi una morsa e pace
	}
	
	@Override
	public void rimuoviMorsa(Totem totem, TipoMorsa tipoMorsa) {
		// Attenzione a gestire il try catch per capire se ci sono morse di quel tipo da rimuovere
	}
	
	@Override
	public void aggiungiBicicletta(Totem totem, TipoBicicletta tipoBicicletta) {
		// Qui devi capire se c'è spazio, idem careful alle eccezioni
	}
	
	@Override
	public void rimuoviBicicletta(Totem totem, TipoBicicletta tipoBicicletta) {
		// Qui devi controllare se c'è il tipo di bicicletta per rimuoverla
	}
}