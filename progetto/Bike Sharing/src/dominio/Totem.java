package dominio;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

// To-do: mi serve un metodo per prendere una bici tramite id? boh
public class Totem {
	private String id;
	private String indirizzo;
	private List<Morsa> morse;
	
	public Totem(String indirizzo) throws NullPointerException {
		if (indirizzo == null) throw new NullPointerException("L'indirizzo del totem non pu� essere null.");
		
		this.id = UUID.randomUUID().toString();
		this.indirizzo = indirizzo;
		this.morse = new ArrayList<Morsa>();
	}
	
	public Totem(String indirizzo, List<Morsa> morse) throws NullPointerException {
		if (indirizzo == null) throw new NullPointerException("L'indirizzo del totem non pu� essere null.");
		if (morse == null) throw new NullPointerException("La lista di morse riguardante il totem non pu� essere null.");
		
		this.id = UUID.randomUUID().toString();
		this.indirizzo = indirizzo;
		this.morse = new ArrayList<Morsa>(morse);
	}
	
	public Bicicletta noleggiaBicicletta(TipoBicicletta tipo) throws NoSuchElementException {
		for (Morsa m : this.morse)
			if (m.occupata() && m.getBicicletta().getTipo() == tipo) return m.apri();
		throw new NoSuchElementException("Non ci sono biciclette di tipo " + tipo + " disponibili in questa postazione.");
	}
	
	public void restituisciBicicletta(Bicicletta bicicletta) throws IllegalStateException {
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
		throw new IllegalStateException("Non c'� spazio per questa bicicletta di tipo " + bicicletta.getTipo() + " tra le morse di questa postazione.");
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
	
	public int getNumeroMorse() {
		return this.morse.size();
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Totem - ID: " + this.id + ", Indirizzo: " + this.indirizzo + ", Numero di morse: " + this.getNumeroMorse() + "\n[Lista morse relative a questo Totem]:\n");
		for (Morsa m : this.morse) {
			s.append(m.toString() + "\n");
		}
		return s.toString();
	}
}