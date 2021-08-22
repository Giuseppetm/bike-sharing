package dati;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import dominio.Bicicletta;
import dominio.Morsa;
import dominio.TipoBicicletta;
import dominio.TipoMorsa;
import dominio.Totem;

public class MorsaDAOPostgres implements MorsaDAO {
	private ConnessioneDb connessioneDb;
	
	public MorsaDAOPostgres() {
		this.connessioneDb = ConnessioneDb.getIstance();
	}
	
	@Override
	public List<Morsa> getMorse(Totem totem) throws NoSuchElementException {
		// Join con la bicicletta per inizializzare anche loro nelle morse
		Connection connessione = this.connessioneDb.getConnessione();
		List<Morsa> morse = new ArrayList<Morsa>();
		
		try {
			PreparedStatement statement = connessione.prepareStatement("SELECT morsa.id, morsa.posizione, morsa.tipo, bicicletta.id, bicicletta.tipo, bicicletta.danneggiata "
	  				+ "FROM morsa LEFT JOIN bicicletta ON morsa.bicicletta = bicicletta.id WHERE morsa.totem = ? ORDER BY morsa.posizione");
			statement.setString(1, totem.getId());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Bicicletta bicicletta = resultSet.getString(5) == null ? null : new Bicicletta(resultSet.getString(4), TipoBicicletta.valueOf(resultSet.getString(5)), resultSet.getBoolean(6));
				Morsa morsa = new Morsa(resultSet.getString(1), TipoMorsa.valueOf(resultSet.getString(3)), bicicletta);
				morse.add(morsa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (morse.isEmpty()) throw new NoSuchElementException("Non ci sono ancora morse inizializzate su questa postazione con totem.");
		
		return morse;
	}
	
	@Override
	public void rimuoviMorse(Totem totem) {
		System.out.println("Rimuovo tutte le morse dalla postazione con totem; id: " + totem.getId() + ", indirizzo: " + totem.getIndirizzo());
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			PreparedStatement statement = connessione.prepareStatement("DELETE FROM morsa WHERE totem = ?");
			statement.setString(1, totem.getId());
			
        	statement.executeUpdate();
        	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void aggiungiMorsa(Totem totem, TipoMorsa tipoMorsa) {
		System.out.println("Aggiungo morsa di tipo " + tipoMorsa + " alla postazione con totem avente id: " + totem.getId() + ", indirizzo: " + totem.getIndirizzo());
		
		Connection connessione = this.connessioneDb.getConnessione();
		Morsa morsa = new Morsa(tipoMorsa);
		totem.aggiungiMorsa(morsa);
		
		try {
			PreparedStatement statement = connessione.prepareStatement("INSERT INTO morsa VALUES(?,?,?,?,?)");
			statement.setString(1, morsa.getId());
			statement.setInt(2, totem.getNumeroMorse());
			statement.setString(3, morsa.getTipo().toString());
			statement.setNull(4, java.sql.Types.NULL);
			statement.setString(5, totem.getId());
			
        	statement.executeUpdate();
        	statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void rimuoviMorsa(Totem totem, TipoMorsa tipoMorsa) throws IllegalStateException {
		System.out.println("Rimuovo morsa di tipo " + tipoMorsa + " alla postazione con totem avente id: " + totem.getId() + ", indirizzo: " + totem.getIndirizzo());
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			Morsa morsaRimossa = totem.rimuoviMorsaByTipo(tipoMorsa);
			
			PreparedStatement statement = connessione.prepareStatement("DELETE FROM morsa WHERE id = ?");
			statement.setString(1, morsaRimossa.getId());
			
        	statement.executeUpdate();
        	statement.close();
		} catch (IllegalStateException e) {
			throw new IllegalStateException("Impossibile rimuovere questo tipo di morsa dalla postazione: non ce ne sono o sono occupate.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}