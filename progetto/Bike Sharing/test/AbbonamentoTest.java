package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dominio.Abbonamento;
import dominio.CartaDiCredito;
import dominio.TipoAbbonamento;

class AbbonamentoTest {
	/**
	 * Questo metodo verifica il corretto aumentare del numero di ammonizioni.
	 */
	@Test
	void ammonizioneAbbonamentoTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.ANNUALE, c, true);
		Assertions.assertEquals(abb.getAmmonizioni(), 0);
		abb.aggiungiAmmonizione();
		Assertions.assertEquals(abb.getAmmonizioni(), 1);
	}
	
	/**
	 * Questo metodo verifica la corretta attivazione di un abbonamento non attivato.
	 */
	@Test
	void attivazioneAbbonamento() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		Assertions.assertNull(abb.getDataInizio());
		abb.attivaAbbonamento();
		Assertions.assertEquals(abb.getDataInizio(), LocalDate.now());
	}
	
	/**
	 * Questo metodo verifica il corretto sollevamento di una IllegalStateException nel caso si tenti di attivare un abbonamento già attivo.
	 */
	@Test
	void attivazioneAbbonamentoGiàAttivoTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		Assertions.assertNull(abb.getDataInizio());
		abb.attivaAbbonamento();
		Assertions.assertEquals(abb.getDataInizio(), LocalDate.now());
		abb.attivaAbbonamento();
		Assertions.assertThrows(IllegalStateException.class, () -> { abb.attivaAbbonamento(); });
	}
	
	/**
	 * Questo metodo verifica la corretta sospensione di un abbonamento.
	 */
	@Test
	void sospensioneAbbonamentoTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		Assertions.assertEquals(abb.isSospeso(), false);
		abb.sospendiAbbonamento();
		Assertions.assertEquals(abb.isSospeso(), true);
	}
	
	
	/**
	 * Questo metodo verifica il corretto sollevamento di una IllegalStateException nel caso in cui si tenti di sospendere un abbonamento già sospeso.
	 */
	@Test
	void sospensioneAbbonamentoGiàSospesoTest() {
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		abb.sospendiAbbonamento();
		Assertions.assertEquals(abb.isSospeso(), true);
		abb.sospendiAbbonamento();
		Assertions.assertThrows(IllegalStateException.class, () -> { abb.sospendiAbbonamento(); });
	}
}