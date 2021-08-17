package dominio;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

public class Noleggio {
	private String id;
	private Abbonamento abbonamento;
	private Bicicletta bicicletta;
	private Totem totem;
	
	private Timestamp orarioInizioNoleggio;
	private Timestamp orarioFineNoleggio;
	
	public Noleggio(Abbonamento abbonamento, Bicicletta bicicletta, Totem totem) throws NullPointerException {
		if (abbonamento == null) throw new NullPointerException("L'abbonamento non può essere null.");
		if (bicicletta == null) throw new NullPointerException("La bicicletta non può essere null.");
		if (totem == null) throw new NullPointerException("Il totem non può essere null.");
		
		this.id = UUID.randomUUID().toString();
		this.abbonamento = abbonamento;
		this.bicicletta = bicicletta;
		this.totem = totem;
		this.orarioInizioNoleggio = new Timestamp(System.currentTimeMillis()); // L'orario di inizio è quello attuale quando viene creato il noleggio
		this.orarioFineNoleggio = null; // L'orario di fine viene settato quando viene chiamato il metodo apposito per la fine del noleggio
	}
	
	/* Costruttore per noleggi già inizializzati */
	public Noleggio(String id, Abbonamento abbonamento, Bicicletta bicicletta, Totem totem, Timestamp orarioInizioNoleggio, Timestamp orarioFineNoleggio) {
		this.id = id;
		this.abbonamento = abbonamento;
		this.bicicletta = bicicletta;
		this.totem = totem;
		this.orarioInizioNoleggio = orarioInizioNoleggio;
		this.orarioFineNoleggio = orarioFineNoleggio;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Abbonamento getAbbonamento() {
		return this.abbonamento;
	}
	
	public Bicicletta getBicicletta() {
		return this.bicicletta;
	}
	
	public Totem getTotem() {
		return this.totem;
	}
	
	public void terminaNoleggio() {
		this.orarioFineNoleggio = new Timestamp(System.currentTimeMillis());
	}
	
	public Timestamp getOrarioInizioNoleggio() {
		return this.orarioInizioNoleggio;
	}
	
	public Timestamp getOrarioFineNoleggio() {
		return this.orarioFineNoleggio;
	}
	
	/* Durata del noleggio in minuti per poter poi calcolarne il costo complessivo */
	public long getDurataNoleggio() throws IllegalStateException { 
		if (this.orarioFineNoleggio == null) throw new IllegalStateException("Il noleggio non è ancora terminato (orarioFineNoleggio = null), di conseguenza non è possibile ottenerne la durata.");
		long durata = TimeUnit.MILLISECONDS.toSeconds(this.orarioFineNoleggio.getTime() - this.orarioInizioNoleggio.getTime()) / 60;
		return durata;
	}
	
	@Override
	public String toString() {
		String noleggio = "Noleggio - ID: " + this.id + ", OrarioInizio: " + this.orarioInizioNoleggio + ", OrarioFine: " + this.orarioFineNoleggio;
		noleggio += "\nAbbonamento collegato: " + this.abbonamento.getCodice() + ", " + this.abbonamento.getTipo() + "\nBicicletta associata: " + this.bicicletta.getId() + ", " + this.bicicletta.getTipo();
		noleggio += "\nTotem in cui è stato effettuato: " + this.totem.getId() + ", indirizzo: " + this.totem.getIndirizzo();
		return noleggio;
	}
}