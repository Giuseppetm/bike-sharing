package dati;

import java.util.List;
import java.util.NoSuchElementException;

import dominio.Totem;

public interface TotemDAO {
	/* Questo metodo serve per prelevare tutte le postazioni con totem disponibili. */
	public List<Totem> getListaTotem() throws NoSuchElementException;
	
	/* Questo metodo preleva la lista di ID dei totem attualmente esistenti; usato per il debug iniziale. */
	public List<String> getListaTotemID() throws NoSuchElementException; 
	
	/* Questo metodo registra nel database un totem appena inizializzato e quindi senza morse e biciclette collegate. */
	public void aggiungiTotem(Totem totem);
	
	/* Questo metodo rimuove un totem dal sistema. */
	public void rimuoviTotem(Totem totem);
}