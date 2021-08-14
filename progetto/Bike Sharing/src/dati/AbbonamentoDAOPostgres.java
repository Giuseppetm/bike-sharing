package dati;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.sql.Connection;

import dominio.Abbonamento;
import dominio.CartaDiCredito;
import dominio.TipoAbbonamento;

public class AbbonamentoDAOPostgres implements AbbonamentoDAO {
	private ConnessioneDb connessioneDb;
	
	public AbbonamentoDAOPostgres() {
		this.connessioneDb = ConnessioneDb.getIstance();
	}
	
	@Override
	public void registraAbbonamento(Abbonamento abbonamento) throws IllegalArgumentException {
		System.out.println("Registro abbonamento: " + abbonamento.toString());
		Connection connessione = this.connessioneDb.getConnessione();

		/* Registrazione carta di credito */
		String queryCartaDiCredito = "INSERT INTO cartadicredito VALUES (?,?,?)";
		
		try {
			PreparedStatement statementCartaDiCredito = connessione.prepareStatement(queryCartaDiCredito);
			statementCartaDiCredito.setString(1, abbonamento.getCartaDiCredito().getNumero());
			statementCartaDiCredito.setString(2, abbonamento.getCartaDiCredito().getScadenza());
			statementCartaDiCredito.setString(3, abbonamento.getCartaDiCredito().getCvv());
			
			statementCartaDiCredito.executeUpdate();
			statementCartaDiCredito.close();
		} catch(SQLException e) {
			e.printStackTrace();
			if (e.getSQLState().equals("23505")) throw new IllegalArgumentException("E' già presente una carta di credito con questo numero.");
			else throw new IllegalArgumentException("Registrazione carta di credito dell'abbonamento fallita.");
		}
		
		/* Registrazione abbonamento */
		String queryRegistrazioneAbbonamento = "INSERT INTO abbonamento VALUES (?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement statementAbbonamento = connessione.prepareStatement(queryRegistrazioneAbbonamento);
			statementAbbonamento.setString(1, abbonamento.getCodice());
			statementAbbonamento.setString(2, abbonamento.getPassword());
			statementAbbonamento.setString(3, abbonamento.getTipo().toString());
			statementAbbonamento.setBoolean(4, abbonamento.isStudente());
			statementAbbonamento.setBoolean(5, abbonamento.isSospeso());
			statementAbbonamento.setDate(6, java.sql.Date.valueOf(abbonamento.getDataAcquisto()));
			if(abbonamento.getDataInizio() != null) statementAbbonamento.setDate(7, java.sql.Date.valueOf(abbonamento.getDataInizio()));
			else statementAbbonamento.setNull(7, java.sql.Types.NULL);
			statementAbbonamento.setDate(8, java.sql.Date.valueOf(abbonamento.getDataScadenzaValidità()));
			statementAbbonamento.setInt(9, abbonamento.getAmmonizioni());
			statementAbbonamento.setString(10, abbonamento.getCartaDiCredito().getNumero());
			
			statementAbbonamento.executeUpdate();
			statementAbbonamento.close();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Registrazione abbonamento fallita.");
		}
	}
	
	@Override
	public Abbonamento effettuaLogin(String codice, String password) throws NoSuchElementException, IllegalStateException {
		Abbonamento abbonamento = null;
		System.out.println("\nLogin abbonamento, codice: " + codice + ", password: " + password);
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			String queryLoginAbbonamento = "SELECT abb.codice, abb.password, abb.tipo, abb.studente, abb.sospeso, abb.dataacquisto, abb.datainizio, abb.datascadenzavalidità, abb.ammonizioni, carta.numero, carta.scadenza, carta.cvv "
					+ "FROM abbonamento AS abb JOIN cartadicredito AS carta ON abb.cartadicredito = carta.numero "
					+ "WHERE abb.codice = ? AND abb.password = ?";
			
			PreparedStatement statementAbbonamento = connessione.prepareStatement(queryLoginAbbonamento);
			statementAbbonamento.setString(1, codice);
			statementAbbonamento.setString(2, password);
			
			ResultSet resultSet = statementAbbonamento.executeQuery();
			if(resultSet.next()) {
				if (resultSet.getBoolean(5)) throw new IllegalStateException("L'abbonamento è stato sospeso; impossibile effettuare il login.");
				CartaDiCredito carta = new CartaDiCredito(resultSet.getString(10), resultSet.getString(11), resultSet.getString(12));
				abbonamento = new Abbonamento(
					resultSet.getString(1), 
					resultSet.getString(2), 
					TipoAbbonamento.valueOf(resultSet.getString(3)), 
					carta, 
					resultSet.getBoolean(4), 
					resultSet.getBoolean(5), 
					resultSet.getDate(6).toLocalDate(), 
					resultSet.getDate(7) != null ? resultSet.getDate(7).toLocalDate() : null, 
					resultSet.getDate(8).toLocalDate(), 
					resultSet.getInt(9)
				);
			} else throw new NoSuchElementException("Non è stato possibile effettuare il login in quanto i dati inseriti non sono validi.");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return abbonamento;
	}

	@Override
	public void attivaAbbonamento(Abbonamento abbonamento) {
		try {
			abbonamento.attivaAbbonamento();
		} catch (IllegalStateException e) {
			throw new IllegalArgumentException("L'abbonamento è già attivo!");
		}
		
		System.out.println("Attivamento abbonamento, codice: " + abbonamento.getCodice());
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			String queryAttivaAbbonamento = "UPDATE abbonamento SET datainizio = ? WHERE codice = ?";
			PreparedStatement statementAbbonamento = connessione.prepareStatement(queryAttivaAbbonamento);
			statementAbbonamento.setDate(1, java.sql.Date.valueOf(abbonamento.getDataInizio()));
			statementAbbonamento.setString(2, abbonamento.getCodice());
			
			statementAbbonamento.executeUpdate();
			statementAbbonamento.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void ammonisciAbbonamento(Abbonamento abbonamento) {
		System.out.println("Ammonizione abbonamento, codice: " + abbonamento.getCodice());
		abbonamento.aggiungiAmmonizione();
		
		if (abbonamento.getAmmonizioni() == 3) this.sospendiAbbonamento(abbonamento);
		
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			String queryAmmonisciAbbonamento = "UPDATE abbonamento SET ammonizioni = ? WHERE codice = ?";
			PreparedStatement statementAbbonamento = connessione.prepareStatement(queryAmmonisciAbbonamento);
			statementAbbonamento.setInt(1, abbonamento.getAmmonizioni());
			statementAbbonamento.setString(2, abbonamento.getCodice());
			
			statementAbbonamento.executeUpdate();
			statementAbbonamento.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sospendiAbbonamento(Abbonamento abbonamento) {
		System.out.println("Sospensione abbonamento, codice: " + abbonamento.getCodice());
		abbonamento.sospendiAbbonamento();
		Connection connessione = this.connessioneDb.getConnessione();
		
		try {
			String querySospendiAbbonamento = "UPDATE abbonamento SET sospeso = ? WHERE codice = ?";
			PreparedStatement statementAbbonamento = connessione.prepareStatement(querySospendiAbbonamento);
			statementAbbonamento.setBoolean(1, abbonamento.isSospeso());
			statementAbbonamento.setString(2, abbonamento.getCodice());
			
			statementAbbonamento.executeUpdate();
			statementAbbonamento.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}