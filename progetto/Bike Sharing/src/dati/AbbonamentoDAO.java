package dati;
import java.util.NoSuchElementException;

import dominio.Abbonamento;

public interface AbbonamentoDAO {
	public void registraAbbonamento(Abbonamento abbonamento) throws IllegalArgumentException;

	//public Abbonamento aggiornaAbbonamento(Abbonamento abbonamento) throws IllegalArgumentException;
	
	public Abbonamento effettuaLogin(String codice, String password) throws NoSuchElementException;
	
	public void attivaAbbonamento(Abbonamento abbonamento) throws NoSuchElementException, IllegalArgumentException;
	
	public void ammonisciAbbonamento(Abbonamento abbonamento) throws NoSuchElementException;
	
	public void sospendiAbbonamento(Abbonamento abbonamento) throws NoSuchElementException, IllegalArgumentException;
}