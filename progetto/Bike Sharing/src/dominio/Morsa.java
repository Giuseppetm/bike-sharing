package dominio;
import java.util.UUID;

public class Morsa {
	private String id;
	private Bicicletta bicicletta;
	private TipoMorsa tipo;
	private StatoMorsa stato;
	
	public Morsa(TipoMorsa tipo) {
		this.id = UUID.randomUUID().toString();
		this.tipo = tipo;
		this.bicicletta = null;
		this.stato = StatoMorsa.DISPONIBILE;
	}
	
	public Morsa(TipoMorsa tipo, Bicicletta bicicletta) {
		this.id = UUID.randomUUID().toString();
		this.tipo = tipo;
		this.bicicletta = bicicletta;
		this.stato = StatoMorsa.OCCUPATA;
	}
	
	/* Costruttore per morse già inizializzate */
	public Morsa(String id, TipoMorsa tipo, Bicicletta bicicletta) {
		this.id = id;
		this.tipo = tipo;
		this.bicicletta = bicicletta;
		this.stato = bicicletta == null ? StatoMorsa.DISPONIBILE : StatoMorsa.OCCUPATA;
	}
	
	public Bicicletta apri() throws IllegalStateException {
		if (this.stato == StatoMorsa.DISPONIBILE) throw new IllegalStateException("Non posso aprire una morsa senza bicicletta.");
		Bicicletta b = this.bicicletta;
		this.stato = StatoMorsa.DISPONIBILE;
		this.bicicletta = null;
		System.out.println(b);
		return b;
	}
	
	public void chiudi(Bicicletta bicicletta) throws NullPointerException, IllegalArgumentException {
		if (bicicletta == null) throw new NullPointerException("La bici rimessa nella morsa non può essere null.");
		if ( (this.tipo == TipoMorsa.NORMALE && (bicicletta.getTipo() == TipoBicicletta.ELETTRICA || bicicletta.getTipo() == TipoBicicletta.ELETTRICA_SEGGIOLINO)) 
			|| (this.tipo == TipoMorsa.ELETTRICA && bicicletta.getTipo() == TipoBicicletta.NORMALE))
			throw new IllegalArgumentException("La bici che stai tentando di inserire nella morsa non è compatibile.");
		
		this.bicicletta = bicicletta;
		this.stato = StatoMorsa.OCCUPATA;
	}
	
	public String getId() {
		return this.id;
	}
 	
	public Bicicletta getBicicletta() throws IllegalStateException {
		if (this.bicicletta == null) throw new IllegalStateException("La bicicletta non è stata inizializzata in questa morsa.");
		return this.bicicletta;
	}
	
	public TipoMorsa getTipo() {
		return this.tipo;
	}
	
	public boolean occupata() {
		return this.stato == StatoMorsa.OCCUPATA;
	}
	
	@Override
	public String toString() {
		return "Morsa - ID: " + this.id + ", Tipo: " + this.tipo + "; Stato: " + this.stato + "; Bicicletta: " + this.bicicletta;
	}
}