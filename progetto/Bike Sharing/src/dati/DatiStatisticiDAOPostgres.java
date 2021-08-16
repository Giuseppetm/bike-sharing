package dati;

public class DatiStatisticiDAOPostgres implements DatiStatisticiDAO {
	public int getNumeroAbbonamenti() {
		return 0;
	}
	
	public int getNumeroAbbonamentiAttivi() {
		return 0;
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