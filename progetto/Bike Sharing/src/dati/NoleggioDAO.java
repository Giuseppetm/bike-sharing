package dati;

import java.util.List;
import java.util.NoSuchElementException;

import dominio.Abbonamento;
import dominio.Noleggio;
import dominio.TipoBicicletta;
import dominio.Totem;

public interface NoleggioDAO {
	/* Questo metodo preleva la lista di noleggi che sono stati effettuati da un utente con uno specifico abbonamento. */
	public List<Noleggio> getListaNoleggi(Abbonamento abbonamento) throws NoSuchElementException;
	
	/* Questo metodo permette di iniziare un noleggio. */
	public void iniziaNoleggio(Abbonamento abbonamento, Totem totem, TipoBicicletta tipoBicicletta) throws IllegalStateException;
	
	/* Questo metodo permette di finire un noleggio. */
	public Noleggio finisciNoleggio(Abbonamento abbonamento, Totem totem) throws IllegalStateException;
	
	/* Questo metodo restituisce il noleggio attualmente in corso relativo ad un determinato abbonamento. */
	public Noleggio getNoleggioInCorso(Abbonamento abbonamento) throws NoSuchElementException;
	
	/* Questo metodo indica se l'abbonamento specificato ha o meno un noleggio attualmente in corso. */
	public boolean hasNoleggioInCorso(Abbonamento abbonamento);
	
	/* Questo metodo serve a calcolare se ci sono stati noleggi relativi ad un abbonamento negli ultimi cinque minuti. */
	public boolean passatiCinqueMinuti(Abbonamento abbonamento);
	
	/* Questo metodo serve a calcolare se è il primo noleggio dell'abbonamento e va quindi attivato. */
	public boolean isPrimoNoleggio(Abbonamento abbonamento); 
}