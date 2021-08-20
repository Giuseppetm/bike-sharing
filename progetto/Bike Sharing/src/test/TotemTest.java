package test;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import dominio.Bicicletta;
import dominio.Morsa;
import dominio.TipoBicicletta;
import dominio.TipoMorsa;
import dominio.Totem;

public class TotemTest {
	/* Questo test verifica che non vengano sollevate eccezioni nel caso si noleggi una bicicletta che si sa essere disponibile */
	@Test
	public void noleggioBiciclettaTest() {
		Totem totem = new Totem("Via Dennissonis 66");
		Bicicletta bici = new Bicicletta(TipoBicicletta.ELETTRICA);
        Morsa morsa = new Morsa(TipoMorsa.ELETTRICA);
        morsa.chiudi(bici);
        totem.aggiungiMorsa(morsa);
        Assertions.assertDoesNotThrow(() -> { totem.noleggiaBicicletta(TipoBicicletta.ELETTRICA); });
	}
	
	/* Questo test verifica che venga sollevata un'eccezione NoSuchElementException nel caso si tenti di noleggiare un tipo di bicicletta non disponibile nella postazione */
	@Test
	public void noleggioBiciclettaNonDisponibileTest() {
		Totem totem = new Totem("Via Dennissonis 66");
        Morsa morsa = new Morsa(TipoMorsa.ELETTRICA);
        totem.aggiungiMorsa(morsa);
        Assertions.assertThrows(NoSuchElementException.class, () -> { totem.noleggiaBicicletta(TipoBicicletta.ELETTRICA); });
	}
	
	/* Questo test verifica che non vengano sollevate eccezioni nel caso si restituisca una bicicletta in una postazione con spazio libero */
	@Test
	public void restituzioneBiciclettaTest() {
		Totem totem = new Totem("Via Dennissonis 66");
		Bicicletta bici = new Bicicletta(TipoBicicletta.ELETTRICA);
        Morsa morsa = new Morsa(TipoMorsa.ELETTRICA);
        totem.aggiungiMorsa(morsa);
        Assertions.assertDoesNotThrow(() -> { totem.restituisciBicicletta(bici); });
	}
	
	/* Questo test verifica venga sollevata un'eccezione IllegalStateException nel caso si tenti di restituire una bicicletta in una postazione senza spazio libero */
	@Test
	public void restituzioneBiciclettaSenzaMorsaLiberaTest() {
		Totem totem = new Totem("Via Dennissonis 66");
		Bicicletta bici = new Bicicletta(TipoBicicletta.ELETTRICA);
		Assertions.assertThrows(IllegalStateException.class, () -> { totem.restituisciBicicletta(bici); });
	}
	
	@Test
	public void numeroMorseOccupateTest() {
		Totem totem = new Totem("Via Dennissonis 66");
		Bicicletta bici = new Bicicletta(TipoBicicletta.ELETTRICA);
        Morsa morsa = new Morsa(TipoMorsa.ELETTRICA);
        morsa.chiudi(bici);
        totem.aggiungiMorsa(morsa);
        Assertions.assertEquals(totem.getNumeroMorseOccupate(), 1);
	}
}