package dominio;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Totem {
	private String id;
	private String indirizzo;
	private List<Morsa> morse;
	
	public Totem(String id, String indirizzo) throws NullPointerException {
		if (id == null) throw new NullPointerException("L'id del totem non può essere null.");
		if (indirizzo == null) throw new NullPointerException("L'indirizzo del totem non può essere null.");
		
		this.id = id;
		this.indirizzo = indirizzo;
		this.morse = new ArrayList<Morsa>(); // In questo caso la lista di morse è inizialmente vuota
	}
	
	public Totem(String id, String indirizzo, List<Morsa> morse) throws NullPointerException {
		if (id == null) throw new NullPointerException("L'id del totem non può essere null.");
		if (indirizzo == null) throw new NullPointerException("L'indirizzo del totem non può essere null.");
		if (morse == null) throw new NullPointerException("La lista di morse riguardante il totem non può essere null.");
		
		this.id = id;
		this.indirizzo = indirizzo;
		this.morse = new ArrayList<Morsa>(morse);
	}
	
	public Bicicletta noleggiaBicicletta(TipoBicicletta tipo) throws NoSuchElementException {
		for (Morsa m : this.morse)
			if (m.occupata() && m.getBicicletta().getTipo() == tipo) return m.apri();
		throw new NoSuchElementException("Non ci sono biciclette di tipo " + tipo + " disponibili in questa postazione.");
	}
	
	public void restituisciBici(Bicicletta bicicletta) throws IllegalStateException {
		for (Morsa m : this.morse) {
			if (!m.occupata()) {
				try {
					m.chiudi(bicicletta);
					return;
				} catch (IllegalArgumentException e) {
					continue;
				}
			}
		}
		throw new IllegalStateException("Non c'è spazio per questa bicicletta di tipo " + bicicletta.getTipo() + " tra le morse di questa postazione.");
	}
	
	public void comunicaDanni(Bicicletta bicicletta) {
		bicicletta.setDanneggiata(true);
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getIndirizzo() {
		return this.indirizzo;
	}
	
	public List<Morsa> getMorse() {
		List<Morsa> morseTemp = new ArrayList<Morsa>(this.morse);
		return morseTemp;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Totem - Id: " + this.id + ", Indirizzo: " + this.indirizzo + "\n[Lista morse]:\n");
		for (Morsa m : this.morse) {
			s.append(m.toString() + "\n");
		}
		return s.toString();
	}
}