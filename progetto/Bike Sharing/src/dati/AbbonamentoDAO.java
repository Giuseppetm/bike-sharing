package dati;
import java.util.NoSuchElementException;

import dominio.Abbonamento;

public interface AbbonamentoDAO {
	public void registraAbbonamento(Abbonamento abbonamento) throws IllegalArgumentException;

	public Abbonamento effettuaLogin(String codice, String password) throws NoSuchElementException, IllegalStateException;
	
	public void attivaAbbonamento(Abbonamento abbonamento);
	
	public void ammonisciAbbonamento(Abbonamento abbonamento);
	
	public void sospendiAbbonamento(Abbonamento abbonamento);
}