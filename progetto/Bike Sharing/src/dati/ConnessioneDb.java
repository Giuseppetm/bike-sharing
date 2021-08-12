package dati;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// To-do: mi sa che bisogna aggiustare i try catch e dare altri throw che vengono poi gestiti al livello superiore
public class ConnessioneDb {
	private static ConnessioneDb connessioneDb;
	private Connection connessione;
	
	private ConnessioneDb() {}
    
    public static ConnessioneDb getIstance() {
    	if (connessioneDb == null) connessioneDb = new ConnessioneDb();
    	return connessioneDb;
    }
    
    public void connetti() {
		String urlConnection = "jdbc:postgresql://localhost/bike_sharing?currentSchema=bike_sharing&user=postgres&password=postgres";
		System.out.println("@@@ Connesso al database PostgreSQL! @@@");
		
		try {
			this.connessione = DriverManager.getConnection(urlConnection);
		} catch (SQLException e) {
	        System.out.println("## Connessione non riuscita ##");
	        e.printStackTrace();
	    }
    }
    
    public Connection getConnessione() throws IllegalStateException {
    	if (this.connessione == null) throw new IllegalStateException("La connessione non è stata ancora inizializzata.");
    	return this.connessione;
    }
    
    public void chiudiConnessione() throws IllegalStateException {
    	if (this.connessione == null) throw new IllegalStateException("La connessione non è stata ancora inizializzata.");
    	try {
    		this.connessione.close();
    	} catch (SQLException e) {
    		System.out.println("## Disconnessione non riuscita ##");
    		e.printStackTrace();
    	}
    }
}