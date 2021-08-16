package dati;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dominio.Morsa;

public class DatiStatisticiDAOPostgres implements DatiStatisticiDAO {
	private ConnessioneDb connessioneDb;
	
	public DatiStatisticiDAOPostgres() {
		this.connessioneDb = ConnessioneDb.getIstance();
	}
	
	public int getNumeroAbbonamenti() {
		Connection connessione = this.connessioneDb.getConnessione();
		System.out.println("Calcolo numero abbonamenti..");
		int numeroAbbonamenti = 0;
		
		try {
			Statement statement = connessione.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM abbonamento");
			while (resultSet.next())
				numeroAbbonamenti = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numeroAbbonamenti;
	}
	
	public int getNumeroAbbonamentiAttivi() {
		Connection connessione = this.connessioneDb.getConnessione();
		System.out.println("Calcolo numero abbonamenti attivi..");
		int numeroAbbonamentiAttivi = 0;
		
		try {
			Statement statement = connessione.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM abbonamento WHERE datainizio IS NOT NULL");
			while (resultSet.next())
				numeroAbbonamentiAttivi = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numeroAbbonamentiAttivi;
	}
	
	public int getNumeroAbbonamentiSospesi() {
		return 0;
	}
	
	public int getNumeroBicicletteDanneggiate() {
		return 0;
	}

	public int getNumeroNoleggiEffettuati() {
		return 0;
	}
	
	// Per vedere quanti totem ci sono nel sistema
	public int getNumeroTotem() {
		return 0;
	}
	
	// Totem con più noleggi associati, basta restituire l'indirizzo
	public String getTotemPiùUtilizzato() {
		return "Il totem di dennissone il farinone.";
	}
}