package dati;

import java.util.NoSuchElementException;

import dominio.Bicicletta;
import dominio.TipoBicicletta;
import dominio.Totem;

public interface BiciclettaDAO {
	public Bicicletta noleggiaBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws NoSuchElementException;
	
	public void restituisciBicicletta(Totem totem, Bicicletta bicicletta) throws IllegalStateException;
	
	public void aggiungiBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws IllegalStateException;
	
	public void rimuoviBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws NoSuchElementException;
	
	public void comunicaDanni(Bicicletta bicicletta);
	
	public void riparaBicicletta(Bicicletta bicicletta) throws IllegalStateException;
	
	/* Serve a calcolare la posizione della bicicletta che verrà poi noleggiata. */
	public int getPosizioneNellaPostazione(Totem totem, TipoBicicletta bicicletta) throws IllegalStateException;
}