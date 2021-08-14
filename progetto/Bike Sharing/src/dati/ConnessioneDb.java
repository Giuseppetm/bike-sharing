package dati;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDb {
	private static ConnessioneDb connessioneDb;
	private Connection connessione;
	
	private ConnessioneDb() {}
    
    public static ConnessioneDb getIstance() {
    	if (connessioneDb == null) connessioneDb = new ConnessioneDb();
    	return connessioneDb;
    }
    
    public void connetti() throws SQLException {
		String urlConnection = "jdbc:postgresql://localhost/bike_sharing?currentSchema=bike_sharing&user=postgres&password=postgres";
		
		try {
			this.connessione = DriverManager.getConnection(urlConnection);
		} catch (SQLException e) {
			throw new SQLException("## Connessione non riuscita ##");
	    }
    }
    
    public Connection getConnessione() throws IllegalStateException {
    	if (this.connessione == null) throw new IllegalStateException("La connessione non è stata ancora inizializzata.");
    	return this.connessione;
    }
    
    public void chiudiConnessione() throws IllegalStateException, SQLException {
    	if (this.connessione == null) throw new IllegalStateException("La connessione non è stata ancora inizializzata.");
    	try {
    		this.connessione.close();
    	} catch (SQLException e) {
    		throw new SQLException("## Disconnessione non riuscita ##");
    	}
    }
}