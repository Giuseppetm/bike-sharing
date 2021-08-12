package dominio;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

public class Noleggio {
	private String id;
	private Abbonamento abbonamento;
	private Bicicletta bicicletta;
	
	private Timestamp orarioInizioNoleggio;
	private Timestamp orarioFineNoleggio;
	
	public Noleggio(Abbonamento abbonamento, Bicicletta bicicletta) throws NullPointerException {
		if (abbonamento == null) throw new NullPointerException("L'abbonamento non può essere null.");
		if (bicicletta == null) throw new NullPointerException("La bicicletta non può essere null.");
		
		this.id = UUID.randomUUID().toString();
		this.abbonamento = abbonamento;
		this.bicicletta = bicicletta;
		this.orarioInizioNoleggio = new Timestamp(System.currentTimeMillis()); // L'orario di inizio è quello attuale quando viene creato il noleggio
		this.orarioFineNoleggio = null; // L'orario di fine viene settato quando viene chiamato il metodo apposito per la fine del noleggio
	}
	
	public Abbonamento getAbbonamento() {
		return this.abbonamento;
	}
	
	public Bicicletta getBicicletta() {
		return this.bicicletta;
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
	
	public long getDurataNoleggio() throws IllegalStateException { // Magari li trasformo in minuti dividendo per 60?
		if (this.orarioFineNoleggio == null) throw new IllegalStateException("Il noleggio non è ancora terminato (orarioFineNoleggio = null), di conseguenza non è possibile ottenerne la durata.");
		return TimeUnit.MILLISECONDS.toSeconds(this.orarioFineNoleggio.getTime() - this.orarioInizioNoleggio.getTime());
	}
	
	@Override
	public String toString() {
		String noleggio = "Noleggio - ID: " + this.id + ", OrarioInizio: " + this.orarioInizioNoleggio + ", OrarioFine: " + this.orarioFineNoleggio;
		noleggio += "\nAbbonamento collegato: " + this.abbonamento.getCodice() + ", " + this.abbonamento.getTipo() + "\nBicicletta associata: " + this.bicicletta.getId() + ", " + this.bicicletta.getTipo();
		return noleggio;
	}
}