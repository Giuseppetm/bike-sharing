package dominio;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

public class Noleggio {
	private Abbonamento abbonamento;
	private Bicicletta bicicletta;
	
	private Timestamp orarioInizioNoleggio;
	private Timestamp orarioFineNoleggio;
	
	public Noleggio(Abbonamento abbonamento, Bicicletta bicicletta) throws NullPointerException {
		if (abbonamento == null) throw new NullPointerException("L'abbonamento non può essere null.");
		if (bicicletta == null) throw new NullPointerException("La bicicletta non può essere null.");
		if (orarioInizioNoleggio == null) throw new NullPointerException("L'orario di inizio noleggio non può essere null.");
		
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
	
	public long getDurataNoleggio() throws IllegalStateException {
		if (this.orarioFineNoleggio == null) throw new IllegalStateException("Il noleggio non è ancora terminato (orarioFineNoleggio = null), di conseguenza non è possibile ottenerne la durata.");
		return TimeUnit.MILLISECONDS.toSeconds(this.orarioInizioNoleggio.getTime() - this.orarioFineNoleggio.getTime());
	}
	
	@Override
	public String toString() {
		String noleggio = "Noleggio - OrarioInizio: " + this.orarioInizioNoleggio + ", OrarioFine: " + this.orarioFineNoleggio;
		noleggio += "\nAbbonamento collegato: " + this.abbonamento + "\nBicicletta associata: " + this.bicicletta;
		return noleggio;
	}
}