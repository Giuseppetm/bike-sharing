package dominio;
import java.time.LocalDate;

// To-do: decidere come gestire il fatto che l'abbonamento deve essere valido fino alla fine dell'abbonamento (il problema è che la fine non è determinata all'inizio nel caso di giorn. e sett.
// To-do: da gestire l'attivazione dell'abbonamento in caso di giornaliero e settimanale che va fatta al primo noleggio

public class Abbonamento {
	private TipoAbbonamento tipo;
	private String codice;
	private String password;
	
	private CartaDiCredito carta;	
	
	private int ammonizioni;
	private boolean sospeso;
	private boolean studente;
	private LocalDate dataAcquisto;
	private LocalDate dataInizio;
	
	public Abbonamento(String codice, String password, TipoAbbonamento tipo, CartaDiCredito carta, boolean studente) throws NullPointerException, IllegalArgumentException {
		if (codice == null || password == null || carta == null) throw new NullPointerException("I parametri non possono essere null.");
		//if (!CartaDiCredito.controllaScadenzaAbbonamento(this.calcolaScadenzaAbbonamento(), this.carta.getScadenza())) throw new IllegalArgumentException("La scadenza della carta di credito non può avvenire prima della fine dell'abbonamento.");
		
		this.codice = codice;
		this.password = password;
		this.tipo = tipo;
		this.carta = carta;
		this.studente = studente;
		this.dataAcquisto = LocalDate.now();
		this.sospeso = false;
		
		if (this.tipo == TipoAbbonamento.ANNUALE) this.attivaAbbonamento();
	}
	
	public String getCodice() {
		return this.codice;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public TipoAbbonamento getTipo() {
		return this.tipo;
	}
	
	public LocalDate getDataAcquisto() {
		return this.dataAcquisto;
	}
	
	public LocalDate getDataInizio() {
		return this.dataInizio;
	}
	
	public int getAmmonizioni() {
		return this.ammonizioni;
	}
	
	public CartaDiCredito getCartaDiCredito() {
		return this.carta;
	}

	public void attivaAbbonamento() {
		if (this.dataInizio != null) throw new IllegalStateException("L'abbonamento è stato già attivato.");
		this.dataInizio = LocalDate.now();
		this.ammonizioni = 0; // Ha più senso inizializzare qui le ammonizioni
	}
	
	public void aggiungiAmmonizione() {
		this.ammonizioni++;
	}
	
	public void sospendiAbbonamento() {
		if (this.sospeso) throw new IllegalStateException("L'abbonamento è stato già annullato.");
		this.sospeso = true;
	}
	
	public boolean isSospeso() {
		return this.sospeso;
	}
	
	public boolean isStudente() {
		return this.studente;
	}
	
	public boolean isPersonaleComune() {
		return (this.tipo == TipoAbbonamento.PERSONALE_SERVIZIO);
	}
	
	public LocalDate calcolaScadenzaAbbonamento() {
		if (this.dataInizio == null) return null; // Se la carta non è ancora stata attivata restituisco null => (Non c'è ancora una data di scadenza quindi).
		if (this.tipo == TipoAbbonamento.GIORNALIERO) return this.dataInizio;
		if (this.tipo == TipoAbbonamento.SETTIMANALE) return this.dataInizio.plusDays(7);
		if (this.tipo == TipoAbbonamento.ANNUALE) return this.dataInizio.plusYears(1);
		return this.dataInizio.plusYears(3); // Il personale del servizio deve rinnovare l'abbonamento ogni 3 anni.
	}
	
	@Override
	public String toString() {
		String abbonamento = "Abbonamento - Codice: " + this.codice + ", Password: " + this.password + ", Tipo: " + this.tipo + ", Ammonizioni: " + this.ammonizioni + ", Sospeso? " + this.sospeso + ", Studente? " + this.studente + ", Personale servizio? " + this.isPersonaleComune();
		abbonamento += "\nData acquisto: " + this.dataAcquisto + ", Data inizio: " + this.dataInizio + ", Data di scadenza: " + this.calcolaScadenzaAbbonamento();
		abbonamento += "\nCarta di credito collegata: " + this.carta;
		return abbonamento;
	}
}