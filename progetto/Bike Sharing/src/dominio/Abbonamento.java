package dominio;
import java.time.LocalDate;
import java.util.UUID;

// To-do: da gestire l'attivazione dell'abbonamento in caso di giornaliero e settimanale che va fatta al primo noleggio.
// To-do: dire all'utente al momento dell'abbonamento che ha tempo sino al ... per utilizzare l'abbonamento giornaliero o settimanale.

public class Abbonamento {
	private TipoAbbonamento tipo;
	private String codice;
	private String password;
	
	private CartaDiCredito carta;	
	
	private int ammonizioni;
	private boolean sospeso;
	private boolean studente;
	private LocalDate dataAcquisto;
	private LocalDate dataScadenzaValidità;
	private LocalDate dataInizio;
	
	public Abbonamento(String password, TipoAbbonamento tipo, CartaDiCredito carta, boolean studente) throws NullPointerException, IllegalArgumentException {
		if (password == null || tipo == null || carta == null) throw new NullPointerException("I parametri non possono essere null.");
		if (password.length() > 20) throw new IllegalArgumentException("La lunghezza della password non può essere maggiore di 20 caratteri.");

		this.codice = UUID.randomUUID().toString();
		this.password = password;
		this.tipo = tipo;
		this.carta = carta;
		this.studente = studente;
		this.dataAcquisto = LocalDate.now();
		this.sospeso = false;
		
		if (this.tipo == TipoAbbonamento.ANNUALE || this.tipo == TipoAbbonamento.PERSONALE_SERVIZIO) this.attivaAbbonamento(); // Nel caso del personale penso sia attivato di default dato che è nel db
		this.dataScadenzaValidità = this.calcolaValiditàAbbonamento(tipo, this.carta.getScadenza());
	}
	
	/* Costruttore per abbonamenti già inizializzati (caso di login) */
	public Abbonamento(String codice, String password, TipoAbbonamento tipo, CartaDiCredito carta, boolean studente, boolean sospeso, LocalDate dataAcquisto, LocalDate dataInizio, LocalDate dataScadenzaValidità, int ammonizioni) {
		this.codice = codice;
		this.password = password;
		this.tipo = tipo;
		this.carta = carta;
		this.studente = studente;
		this.dataAcquisto = dataAcquisto;
		this.dataInizio = dataInizio;
		this.dataScadenzaValidità = dataScadenzaValidità;
		this.sospeso = sospeso;
		this.ammonizioni = ammonizioni;
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
	
	public LocalDate getDataScadenzaValidità() {
		return this.dataScadenzaValidità;
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
	
	public LocalDate calcolaValiditàAbbonamento(TipoAbbonamento tipo, String scadenzaCarta) throws IllegalArgumentException {
		// 1) Caso di abbonamento annuale: Se la carta di credito scade prima dell'anno prefissato allora non puoi creare l'abbonamento;
		// 2) Caso di abbonamento giornaliero o settimanale: hai 3 mesi per usarlo, quindi la carta di credito non deve scadere prima dei 3 mesi.
		String s[] = scadenzaCarta.split("/", 2);
		int month = Integer.parseInt(s[0]);
		int year = Integer.parseInt(s[1]);
		LocalDate dataScadenzaCarta = LocalDate.of(year, month, 01);
		
		switch (tipo) {
			case ANNUALE: // Nel caso di abbonamento annuale il giorno di scadenza di validità coincide con il giorno di fine abbonamento
				if (dataScadenzaCarta.isBefore(LocalDate.now().plusYears(1))) throw new IllegalArgumentException("Stai tentando di creare un abbonamento annuale con una carta che scade tra meno di un anno.");
				return LocalDate.now().plusYears(1);
			case GIORNALIERO, SETTIMANALE:
				if (dataScadenzaCarta.isBefore(LocalDate.now().plusMonths(3))) throw new IllegalArgumentException("Stai tentando di creare un abbonamento giornaliero o settimanale con una carta che scade tra meno di 3 mesi.");
				return LocalDate.now().plusMonths(3);
			case PERSONALE_SERVIZIO:
				return this.dataInizio.plusYears(3);
			default:
				throw new UnknownError("E' successo qualcosa di brutto.");
		}
	}
	
	public LocalDate calcolaScadenzaAbbonamento() {
		if (this.dataInizio == null) return this.dataScadenzaValidità; // Caso di abbonamento giornaliero o settimanale non ancora iniziato (comprato ma in attesa di partire).
		if (this.tipo == TipoAbbonamento.GIORNALIERO) return this.dataInizio;
		if (this.tipo == TipoAbbonamento.SETTIMANALE) return this.dataInizio.plusDays(7);
		if (this.tipo == TipoAbbonamento.ANNUALE) return this.dataInizio.plusYears(1);
		return this.dataInizio.plusYears(3); // Il personale del servizio deve rinnovare l'abbonamento ogni 3 anni.
	}
	
	public boolean isScaduto() {
		if(this.calcolaScadenzaAbbonamento().isBefore(LocalDate.now())) return true;
		return false;
	}
	
	@Override
	public String toString() {
		String abbonamento = "Abbonamento - Codice: " + this.codice + ", Password: " + this.password + ", Tipo: " + this.tipo + ", Ammonizioni: " + this.ammonizioni + ", Sospeso? " + this.sospeso + ", Studente? " + this.studente + ", Personale servizio? " + this.isPersonaleComune();
		abbonamento += "\nData acquisto: " + this.dataAcquisto + ", Data inizio: " + this.dataInizio + ", Data di scadenza: " + this.calcolaScadenzaAbbonamento() + ", Data scadenza validità: " + this.dataScadenzaValidità;
		abbonamento += "\nCarta di credito collegata: " + this.carta;
		return abbonamento;
	}
}