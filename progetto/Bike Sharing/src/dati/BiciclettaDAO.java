package dati;

import java.util.List;
import java.util.NoSuchElementException;

import dominio.Bicicletta;
import dominio.TipoBicicletta;
import dominio.Totem;

public interface BiciclettaDAO {
	/* Questo metodo permette di noleggiare una bicicletta in base al tipo richiesto. */
	public Bicicletta noleggiaBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws NoSuchElementException;
	
	/* Questo metodo permette di restituire una bicicletta in una postazione con totem. */
	public void restituisciBicicletta(Totem totem, Bicicletta bicicletta) throws IllegalStateException;
	
	/* Questo metodo permette di aggiungere una bicicletta in base al tipo indicato ad una postazione con totem. */
	public void aggiungiBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws IllegalStateException;
	
	/* Questo metodo permette di rimuovere una bicicletta in base al tipo indicato da una postazione con totem. */
	public void rimuoviBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws NoSuchElementException;
	
	/* Questo metodo permette di segnalare che una bicicletta risulta danneggiata. */
	public void comunicaDanni(Bicicletta bicicletta);
	
	/* Questo metodo permette di riparare una bicicletta risultata danneggiata; metodo utilizzabile solo dal personale di servizio. */
	public void riparaBicicletta(Bicicletta bicicletta) throws IllegalStateException;
	
	/* Questo metodo serve a calcolare la posizione della bicicletta che verrà poi noleggiata. */
	public int getPosizioneNellaPostazione(Totem totem, Bicicletta bicicletta) throws IllegalStateException;
	
	/* Questo metodo permette di prelevare la lista di biciclette danneggiate attualmente presenti sul sistema. */
	public List<Bicicletta> getBicicletteDanneggiate() throws NoSuchElementException;
}