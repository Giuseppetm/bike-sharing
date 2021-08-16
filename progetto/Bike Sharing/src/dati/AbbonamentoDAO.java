package dati;

import java.util.List;
import java.util.NoSuchElementException;

import dominio.Abbonamento;

public interface AbbonamentoDAO {
	public List<Abbonamento> getListaAbbonamenti() throws NoSuchElementException; // Per le statistiche
	
	public void registraAbbonamento(Abbonamento abbonamento) throws IllegalArgumentException;

	public Abbonamento effettuaLogin(String codice, String password) throws NoSuchElementException, IllegalStateException;
	
	public void attivaAbbonamento(Abbonamento abbonamento) throws IllegalStateException;
	
	public void ammonisciAbbonamento(Abbonamento abbonamento) throws IllegalStateException;
	
	public void sospendiAbbonamento(Abbonamento abbonamento) throws IllegalStateException;
}