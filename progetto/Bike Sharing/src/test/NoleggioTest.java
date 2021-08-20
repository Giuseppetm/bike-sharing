package test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import dominio.Abbonamento;
import dominio.Bicicletta;
import dominio.CartaDiCredito;
import dominio.Noleggio;
import dominio.TipoAbbonamento;
import dominio.TipoBicicletta;
import dominio.Totem;

public class NoleggioTest {
	/* Questo test verifica che venga sollevata un'eccezione IllegalStateException nel caso si tenti di calcolare la durata di un noleggio non ancora terminato */
	@Test
	public void getDurataNoleggioTest() {
		Bicicletta bici = new Bicicletta("TestID", TipoBicicletta.NORMALE, false);
		CartaDiCredito c = new CartaDiCredito("4079029256061430", "9/2024", "242");
		Abbonamento abb = new Abbonamento("Test123", TipoAbbonamento.SETTIMANALE, c, true);
		Totem totem = new Totem("Via Dennissonis 66");
		Noleggio noleggio = new Noleggio(abb, bici, totem);
		
		Assertions.assertThrows(IllegalStateException.class, () -> { noleggio.getDurataNoleggio(); });
	}
}