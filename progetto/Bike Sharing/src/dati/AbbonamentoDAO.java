package dati;

import java.util.List;
import java.util.NoSuchElementException;

import dominio.Abbonamento;

public interface AbbonamentoDAO {
	/* Questo metodo preleva la lista di abbonamenti attualmente esistenti sul sistema. */
	public List<Abbonamento> getListaAbbonamenti() throws NoSuchElementException;
	
	/* Questo metodo registra un nuovo abbonamento nel sistema. */
	public void registraAbbonamento(Abbonamento abbonamento) throws IllegalArgumentException;

	/* Questo metodo permette di effettuare il login e restituire i dati sull'abbonamento relativo. */
	public Abbonamento effettuaLogin(String codice, String password) throws NoSuchElementException, IllegalStateException;
	
	/* Questo metodo permette di attivare un abbonamento. */
	public void attivaAbbonamento(Abbonamento abbonamento) throws IllegalStateException;
	
	/* Questo metodo permette di ammonire un abbonamento. */
	public void ammonisciAbbonamento(Abbonamento abbonamento) throws IllegalStateException;
	
	/* Questo metodo permette di sospendere un abbonamento. */
	public void sospendiAbbonamento(Abbonamento abbonamento) throws IllegalStateException;
}