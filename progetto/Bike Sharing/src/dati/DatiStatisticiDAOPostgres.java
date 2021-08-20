package dati;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatiStatisticiDAOPostgres implements DatiStatisticiDAO {
	private ConnessioneDb connessioneDb;
	
	public DatiStatisticiDAOPostgres() {
		this.connessioneDb = ConnessioneDb.getIstance();
	}
	
	@Override
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
	
	@Override
	public int getNumeroAbbonamentiAttivi() {
		Connection connessione = this.connessioneDb.getConnessione();
		System.out.println("Calcolo numero abbonamenti attivi..");
		int numeroAbbonamentiAttivi = 0;
		
		try {
			Statement statement = connessione.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM abbonamento WHERE datainizio IS NOT NULL AND sospeso = false");
			while (resultSet.next())
				numeroAbbonamentiAttivi = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numeroAbbonamentiAttivi;
	}
	
	@Override
	public int getNumeroAbbonamentiSospesi() {
		Connection connessione = this.connessioneDb.getConnessione();
		System.out.println("Calcolo numero abbonamenti sospesi..");
		int numeroAbbonamentiSospesi = 0;
		
		try {
			Statement statement = connessione.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM abbonamento WHERE sospeso = true");
			while (resultSet.next())
				numeroAbbonamentiSospesi = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numeroAbbonamentiSospesi;
	}
	
	@Override
	public int getNumeroBicicletteDanneggiate() {
		Connection connessione = this.connessioneDb.getConnessione();
		System.out.println("Calcolo numero biciclette danneggiate..");
		int numeroBicicletteDanneggiate = 0;
		
		try {
			Statement statement = connessione.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM bicicletta WHERE danneggiata = true");
			while (resultSet.next())
				numeroBicicletteDanneggiate = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numeroBicicletteDanneggiate;
	}

	@Override
	public int getNumeroNoleggiEffettuati() {
		Connection connessione = this.connessioneDb.getConnessione();
		System.out.println("Calcolo numero noleggi effettuati..");
		int numeroNoleggiEffettuati = 0;
		
		try {
			Statement statement = connessione.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM noleggio");
			while (resultSet.next())
				numeroNoleggiEffettuati = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numeroNoleggiEffettuati;
	}
	
	@Override
	public int getNumeroTotem() {
		Connection connessione = this.connessioneDb.getConnessione();
		System.out.println("Calcolo numero di totem presenti nel sistema..");
		int numeroTotem = 0;
		
		try {
			Statement statement = connessione.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM totem");
			while (resultSet.next())
				numeroTotem = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numeroTotem;
	}
	
	@Override
	public String getTotemPiùUtilizzato() {
		Connection connessione = this.connessioneDb.getConnessione();
		System.out.println("Calcolo totem più utilizzato nel sistema..");
		String indirizzoTotem = null;

		try {
			Statement statement = connessione.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*), totem.id, totem.indirizzo FROM totem JOIN noleggio ON noleggio.totem = totem.id GROUP BY totem.id");
			while (resultSet.next())
				indirizzoTotem = resultSet.getString(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (indirizzoTotem == null) throw new IllegalStateException("Non ci sono ancora noleggi oppure totem, quindi non è possibile calcolare qual è il totem con maggiori noleggi effettuati.");
		return indirizzoTotem;
	}
}