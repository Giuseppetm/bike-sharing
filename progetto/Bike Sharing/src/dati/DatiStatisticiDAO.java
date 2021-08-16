package dati;

public interface DatiStatisticiDAO {
	public int getNumeroAbbonamenti();
	
	public int getNumeroAbbonamentiAttivi();
	
	public int getNumeroAbbonamentiSospesi();
	
	public int getNumeroBicicletteDanneggiate();

	public int getNumeroNoleggiEffettuati();
	
	public int getNumeroTotem(); // Per vedere quanti totem ci sono nel sistema
	
	public String getTotemPiùUtilizzato(); // Totem con più noleggi associati, basta restituire l'indirizzo
}