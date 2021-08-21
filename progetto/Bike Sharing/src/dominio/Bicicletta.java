package dominio;
import java.util.UUID;

/*@
* @invariant (\forall Bicicletta bici1, bici2; \created(bici1) && \created(bici2); bici1 != bici2 ==> !(bici1.id.equals(bici2.id)));
@*/
public class Bicicletta {
	private String id; 
	private TipoBicicletta tipo;
	private boolean danneggiata;
	
	public Bicicletta(TipoBicicletta tipo) {
		this.id = UUID.randomUUID().toString();
		this.tipo = tipo;
		this.danneggiata = false;
	}
	
	/* Costruttore per biciclette già inizializzate */
	public Bicicletta(String id, TipoBicicletta tipo, boolean danneggiata) {
		this.id = id;
		this.tipo = tipo;
		this.danneggiata = danneggiata;
	}
	
	public String getId() {
		return this.id;
	}
	
	public TipoBicicletta getTipo() {
		return this.tipo;
	}
	
	public void setDanneggiata() {
		this.danneggiata = true;
	}
	
	public void riparaBicicletta() {
		this.danneggiata = false;
	}
	
	public boolean isDanneggiata() {
		return this.danneggiata;
	}
	
	public double calcolaCosto(int minuti, boolean studente) throws IllegalArgumentException {
		if (minuti < 0) throw new IllegalArgumentException("I minuti di noleggio non possono essere negativi.");
		
		if (this.tipo == TipoBicicletta.NORMALE && studente) {
			System.out.printf("Uso bicicletta normale + Status studente => Gratis; Costo: ", minuti);
			return 0;
		} else if (this.tipo == TipoBicicletta.NORMALE && !studente) {
			System.out.printf("Calcolo costo bicicletta normale per uso di %d minuti: ", minuti);
			if (minuti <= 30) return 0;
			else if (minuti > 30 && minuti <= 120) return 0.50;
			else return 2;
		} else {
			System.out.printf("Calcolo costo bicicletta elettrica (elettrica o elettrica con seggiolino) per uso di %d minuti: ", minuti);
			if (minuti <= 30) return 0.25;
			else if (minuti > 30 && minuti <= 60) return 0.50;
			else if (minuti > 60 && minuti <= 90) return 1;
			else if (minuti > 90 && minuti <= 120) return 2;
			else return 4;
		}
	}
	
	@Override
	public String toString() {
		return "Bicicletta - ID: " + this.id + ", Tipo: " + this.tipo + "; danneggiata? " + this.danneggiata;
	}
}