package dati;

import java.util.List;

import dominio.Morsa;
import dominio.TipoMorsa;
import dominio.Totem;

public interface MorsaDAO {
	public List<Morsa> getMorse(Totem totem);
	
	public void rimuoviMorse(Totem totem); // Rimuove tutte le morse associate ad un totem e le relative biciclette
	
	public void aggiungiMorsa(Totem totem, TipoMorsa tipoMorsa);
	
	public void rimuoviMorsa(Totem totem, TipoMorsa tipoMorsa) throws IllegalStateException;
}