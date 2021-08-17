package dati;

import java.util.List;
import java.util.NoSuchElementException;

import dominio.Abbonamento;
import dominio.Noleggio;
import dominio.TipoBicicletta;
import dominio.Totem;

public interface NoleggioDAO {
	public List<Noleggio> getListaNoleggi(Abbonamento abbonamento) throws NoSuchElementException;
	
	public void iniziaNoleggio(Abbonamento abbonamento, Totem totem, TipoBicicletta tipoBicicletta) throws IllegalStateException;
	
	public void finisciNoleggio(Abbonamento abbonamento, Totem totem) throws IllegalStateException;
	
	public Noleggio getNoleggioInCorso(Abbonamento abbonamento) throws NoSuchElementException;
	
	public boolean hasNoleggioInCorso(Abbonamento abbonamento);
	
	public boolean passatiCinqueMinuti(Abbonamento abbonamento); // Per calcolare se ci sono stati noleggi negli ultimi cinque minuti
}