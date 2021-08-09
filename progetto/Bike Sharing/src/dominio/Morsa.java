package dominio;

public class Morsa {
	private Bicicletta bicicletta;
	private TipoMorsa tipo;
	private StatoMorsa stato;
	
	public Morsa(TipoMorsa tipo) {
		this.tipo = tipo;
		this.bicicletta = null;
		this.stato = StatoMorsa.DISPONIBILE;
	}
	
	public Morsa(TipoMorsa tipo, Bicicletta bicicletta) {
		this.tipo = tipo;
		this.bicicletta = bicicletta;
		this.stato = StatoMorsa.OCCUPATA;
	}
	
	public Bicicletta apri() {
		if (this.stato == StatoMorsa.DISPONIBILE) return null;
		Bicicletta b = this.bicicletta;
		this.stato = StatoMorsa.DISPONIBILE;
		this.bicicletta = null;
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
	
	public Bicicletta getBicicletta() {
		return this.bicicletta;
	}
	
	public boolean occupata() {
		return this.stato == StatoMorsa.OCCUPATA;
	}
	
	@Override
	public String toString() {
		return "Morsa - Tipo: " + this.tipo + "; Stato: " + this.stato + "; Bicicletta: " + this.bicicletta;
	}
}