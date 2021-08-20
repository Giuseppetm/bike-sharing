package test;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import dominio.Abbonamento;
import dominio.CartaDiCredito;
import dominio.TipoAbbonamento;

public class AbbonamentoTest {
	/* Questo test verifica il corretto aumentare del numero di ammonizioni. */
	@Test
	public void ammonizioneAbbonamentoTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.ANNUALE, c, true);
		Assertions.assertEquals(abb.getAmmonizioni(), 0);
		abb.aggiungiAmmonizione();
		Assertions.assertEquals(abb.getAmmonizioni(), 1);
	}
	
	/* Questo test verifica la corretta attivazione di un abbonamento non attivato. */
	@Test
	public void attivazioneAbbonamento() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		Assertions.assertNull(abb.getDataInizio());
		abb.attivaAbbonamento();
		Assertions.assertEquals(abb.getDataInizio(), LocalDate.now());
	}
	
	/* Questo test verifica il corretto sollevamento di una IllegalStateException nel caso si tenti di attivare un abbonamento già attivo. */
	@Test
	public void attivazioneAbbonamentoGiàAttivoTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		Assertions.assertNull(abb.getDataInizio());
		abb.attivaAbbonamento();
		Assertions.assertEquals(abb.getDataInizio(), LocalDate.now());
		Assertions.assertThrows(IllegalStateException.class, () -> { abb.attivaAbbonamento(); });
	}
	
	/* Questo test verifica la corretta sospensione di un abbonamento. */
	@Test
	public void sospensioneAbbonamentoTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		Assertions.assertEquals(abb.isSospeso(), false);
		abb.sospendiAbbonamento();
		Assertions.assertEquals(abb.isSospeso(), true);
	}
	
	
	/* Questo test verifica il corretto sollevamento di una IllegalStateException nel caso in cui si tenti di sospendere un abbonamento già sospeso. */
	@Test
	public void sospensioneAbbonamentoGiàSospesoTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		abb.sospendiAbbonamento();
		Assertions.assertEquals(abb.isSospeso(), true);
		Assertions.assertThrows(IllegalStateException.class, () -> { abb.sospendiAbbonamento(); });
	}
	
	/* Questo test verifica che la data di scadenza venga calcolata in modo corretto per ogni tipo di abbonamento. */
	@Test
	public void calcolaScadenzaTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		
		/* Abbonamento giornaliero */
		Abbonamento abbGiornaliero = new Abbonamento("Test123", TipoAbbonamento.GIORNALIERO, c, true);
		abbGiornaliero.attivaAbbonamento();
		Assertions.assertEquals(abbGiornaliero.calcolaScadenzaAbbonamento(), LocalDate.now());
		
		/* Abbonamento settimanale */
		Abbonamento abbSettimanale = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		abbSettimanale.attivaAbbonamento();
		Assertions.assertEquals(abbSettimanale.calcolaScadenzaAbbonamento(), LocalDate.now().plusDays(7));
		
		/* Abbonamento annuale */
		Abbonamento abbAnnuale = new Abbonamento("Test123", TipoAbbonamento.ANNUALE, c, true);
		Assertions.assertEquals(abbAnnuale.calcolaScadenzaAbbonamento(), LocalDate.now().plusYears(1));
		
		/* Abbonamento per il personale di servizio */
		Abbonamento abbPersonaleServizio = new Abbonamento("Test123", TipoAbbonamento.PERSONALE_SERVIZIO, c, true);
		Assertions.assertEquals(abbPersonaleServizio.calcolaScadenzaAbbonamento(), LocalDate.now().plusYears(3));
		
	}
	
	/* Questo test verifica che lo status scaduto dell'abbonamento sia calcolato in modo corretto. */
	@Test
	public void abbonamentoScadutoTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("CodiceTest", "Test123", TipoAbbonamento.GIORNALIERO, c, true, false, LocalDate.now().minusDays(10), LocalDate.now().minusDays(3), LocalDate.now().minusDays(10).plusMonths(3), 0);
		Assertions.assertEquals(abb.isScaduto(), true);
	}
}