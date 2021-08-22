package dati;

public interface DatiStatisticiDAO {
	/* Questo metodo permette di calcolare il numero di abbonamenti attualmente presenti sul sistema. */
	public int getNumeroAbbonamenti();
	
	/* Questo metodo permette di calcolare il numero di abbonamenti attivi attualmente presenti sul sistema. */
	public int getNumeroAbbonamentiAttivi();
	
	/* Questo metodo permette di calcolare il numero di abbonamenti sospesi attualmente presenti sul sistema. */
	public int getNumeroAbbonamentiSospesi();
	
	/* Questo metodo permette di prelevare il numero di biciclette che risultano danneggiate nel sistema. */
	public int getNumeroBicicletteDanneggiate();

	/* Questo metodo permette di calcolare il numero di noleggi fino ad ora effettuati sul sistema. */
	public int getNumeroNoleggiEffettuati();
	
	/* Questo metodo permette di calcolare il numero di postazioni con totem attualmente presenti nel sistema. */
	public int getNumeroTotem();
	
	/* Questo metodo restituisce l'indirizzo del totem più utilizzato nel sistema. */
	public String getTotemPiùUtilizzato();
}