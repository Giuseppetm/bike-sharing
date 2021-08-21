package test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import dominio.CartaDiCredito;

public class CartaDiCreditoTest {
	/* Questo test verifica che venga sollevata un'eccezione IllegalArgumentException nel caso si tenti di creare una carta di credito con numero non valido. */
	@Test
	public void creaCartaNumeroNonValidoTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> { new CartaDiCredito("3241251253254", "9/2024", "242"); });
	}
	
	/* Questo test verifica che non venga sollevata un'eccezione nel caso si tenti di creare una carta di credito con numero valido. */
	@Test
	public void creaCartaNumeroValidoTest() {
		Assertions.assertDoesNotThrow(() -> { new CartaDiCredito("4079029256061430", "9/2024", "242"); });
	}
	
	/* Questo test verifica che venga sollevata un'eccezione IllegalArgumentException nel caso si tenti di creare una carta di credito con scadenza in un formato non valido. */
	@Test
	public void creaCartaFormatoNonValidoTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> { new CartaDiCredito("3241251253254", "9.2023", "242"); });
	}
	
	/* Questo test verifica che non venga sollevata un'eccezione nel caso si tenti di creare una carta di credito con scandenza in un formato valido. */
	@Test
	public void creaCartaFormatoValidoTest() {
		Assertions.assertDoesNotThrow(() -> { new CartaDiCredito("4079029256061430", "9/2024", "242"); });
	}
	
	/* Questo test verifica che venga sollevata un'eccezione IllegalArgumentException nel caso si tenti di creare una carta di credito con cvv non valido. */
	@Test
	public void creaCartaCvvNonValidoTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> { new CartaDiCredito("3241251253254", "9.2023", "1"); });
	}
	
	/* Questo test verifica che non venga sollevata un'eccezione nel caso si tenti di creare una carta di credito con cvv valido. */
	@Test
	public void creaCartaCvvValidoTest() {
		Assertions.assertDoesNotThrow(() -> { new CartaDiCredito("4079029256061430", "9/2024", "423"); });
	}
}