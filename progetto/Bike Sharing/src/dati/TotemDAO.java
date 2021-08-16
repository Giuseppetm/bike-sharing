package dati;

import java.util.List;
import java.util.NoSuchElementException;

import dominio.Totem;

public interface TotemDAO {
	// Questo mi serve per visualizzare tutte le postazioni disponibili poi, e per effettuare operazioni su uno in particolare
	public List<Totem> getListaTotem() throws NoSuchElementException; 
	
	public List<String> getListaTotemID() throws NoSuchElementException;
	
	public void aggiungiTotem(Totem totem);
	
	public void rimuoviTotem(Totem totem);
}