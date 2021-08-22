package dati;

import java.util.List;
import java.util.NoSuchElementException;

import dominio.Morsa;
import dominio.TipoMorsa;
import dominio.Totem;

public interface MorsaDAO {
	/* Questo metodo preleva la lista di morse associate ad una postazione con totem. */
	public List<Morsa> getMorse(Totem totem) throws NoSuchElementException;
	
	/* Questo metodo rimuove tutte le morse associate ad una postazione con totem. */
	public void rimuoviMorse(Totem totem);
	
	/* Questo metodo aggiunge una morsa in base al tipo specificato ad una postazione con totem. */
	public void aggiungiMorsa(Totem totem, TipoMorsa tipoMorsa);
	
	/* Questo metodo rimuove una morsa in base al tipo specificato da una postazione con totem. */
	public void rimuoviMorsa(Totem totem, TipoMorsa tipoMorsa) throws IllegalStateException;
}