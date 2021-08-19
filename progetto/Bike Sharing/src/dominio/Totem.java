package dominio;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Totem {
	private String id;
	private String indirizzo;
	private List<Morsa> morse;
	
	public Totem(String indirizzo) throws NullPointerException {
		if (indirizzo == null) throw new NullPointerException("L'indirizzo del totem non può essere null.");
		
		this.id = UUID.randomUUID().toString();
		this.indirizzo = indirizzo;
		this.morse = new ArrayList<Morsa>();
	}
	
	public Totem(String indirizzo, List<Morsa> morse) throws NullPointerException {
		if (indirizzo == null) throw new NullPointerException("L'indirizzo del totem non può essere null.");
		if (morse == null) throw new NullPointerException("La lista di morse riguardante il totem non può essere null.");
		
		this.id = UUID.randomUUID().toString();
		this.indirizzo = indirizzo;
		this.morse = new ArrayList<Morsa>(morse);
	}
	
	/* Costruttore per totem già inizializzato con lista di morse */
	public Totem(String id, String indirizzo, List<Morsa> morse) {
		this.id = id;
		this.indirizzo = indirizzo;
		this.morse = new ArrayList<Morsa>(morse);
	}
	
	/* Costruttore per totem già inizializzato senza lista di morse */
	public Totem(String id, String indirizzo) {
		this.id = id;
		this.indirizzo = indirizzo;
		this.morse = new ArrayList<Morsa>();
	}
	
	public Bicicletta noleggiaBicicletta(TipoBicicletta tipo) throws NoSuchElementException {
		for (Morsa m : this.morse)
			if (m.occupata() && m.getBicicletta().getTipo() == tipo) return m.apri();
		throw new NoSuchElementException("Non ci sono biciclette di tipo " + tipo + " disponibili in questa postazione.");
	}
	
	public Morsa restituisciBicicletta(Bicicletta bicicletta) throws IllegalStateException {
		for (Morsa m : this.morse) {
			if (!m.occupata()) {
				try {
					m.chiudi(bicicletta);
					return m;
				} catch (IllegalArgumentException e) {
					continue;
				}
			}
		}
		throw new IllegalStateException("Non c'è spazio per questa bicicletta di tipo " + bicicletta.getTipo() + " tra le morse di questa postazione.");
	}
	
	public void aggiungiMorsa(Morsa morsa) {
		this.morse.add(morsa);
	}
	
	public void rimuoviMorsa(Morsa morsa) throws IllegalStateException {
		for (Morsa m : this.morse) {
			if (!m.occupata() && m.getId() == morsa.getId()) {
				try {
					this.morse.remove(m);
					return;
				} catch (IllegalArgumentException e) {
					continue;
				}
			}
		}
		throw new IllegalStateException("Impossibile eliminare questa morsa: pare che non sia in questa postazione oppure è occupata.");
	}
	
	public void aggiungiMorsaByTipo(TipoMorsa tipo) {
		Morsa morsa = new Morsa(tipo);
		this.morse.add(morsa);
	}
	
	public Morsa rimuoviMorsaByTipo(TipoMorsa tipo) throws IllegalStateException {
		for (Morsa m : this.morse) {
			if (!m.occupata() && m.getTipo() == tipo) {
				morse.remove(m);
				return m;
			}
		}
		throw new IllegalStateException("Impossibile rimuovere una morsa di questo tipo dalla postazione in quanto non ce ne sono al momento.");
	}
	
	public void comunicaDanni(Bicicletta bicicletta) {
		bicicletta.setDanneggiata();
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
	
	public int getNumeroMorseOccupate() {
		int i = 0;
		for (Morsa m : this.morse) try { m.getBicicletta(); } catch (Exception e) { i++; }
		return this.morse.size() - i;
	}
	
	/* Questo metodo serve a calcolare la bicicletta che verrà successivamente noleggiata, per scopi legati alla posizione nella postazione con totem. */
	public Bicicletta getBicicletta(TipoBicicletta tipoBicicletta) {
		for (Morsa m : this.morse)
			if (m.occupata() && m.getBicicletta().getTipo() == tipoBicicletta) return m.getBicicletta();
		throw new NoSuchElementException("Non ci sono biciclette di tipo " + tipoBicicletta + " disponibili in questa postazione.");
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Totem - ID: " + this.id + ", Indirizzo: " + this.indirizzo + ", Numero di morse: " + this.getNumeroMorse() + ", Numero di morse occupate: " + this.getNumeroMorseOccupate() + "\n[Lista morse relative a questo Totem]:\n");
		for (Morsa m : this.morse) {
			s.append(m.toString() + "\n");
		}
		return s.toString();
	}
}