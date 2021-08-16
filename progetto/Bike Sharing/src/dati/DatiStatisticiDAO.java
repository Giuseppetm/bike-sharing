package dati;

public interface DatiStatisticiDAO {
	public int getNumeroAbbonamenti();
	
	public int getNumeroAbbonamentiAttivi();
	
	public int getNumeroAbbonamentiSospesi();
	
	public int getNumeroBicicletteDanneggiate();

	public int getNumeroNoleggiEffettuati();
	
	public int getNumeroTotem();
	
	public String getTotemPiùUtilizzato();
}