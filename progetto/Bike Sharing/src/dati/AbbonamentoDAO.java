package dati;
import java.util.NoSuchElementException;

import dominio.Abbonamento;

public interface AbbonamentoDAO {
	public void registraAbbonamento(Abbonamento abbonamento) throws IllegalArgumentException;

	public Abbonamento aggiornaAbbonamento(Abbonamento abbonamento) throws IllegalArgumentException;
	
	public Abbonamento effettuaLogin(String codice, String password) throws NoSuchElementException;
	
	public void attivaAbbonamento(String codice) throws NoSuchElementException;
	
	public void ammonisciAbbonamento(String codice) throws NoSuchElementException;
	
	public void sospendiAbbonamento(String codice) throws NoSuchElementException;
}