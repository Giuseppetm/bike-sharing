package test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import dominio.Bicicletta;
import dominio.TipoBicicletta;

public class BiciclettaTest {
	/* Questo test verifica che una bicicletta segnalata come danneggiata lo diventi effettivamente */
	@Test
	public void danneggiaBicicletta() {
		Bicicletta bici = new Bicicletta("TestID", TipoBicicletta.NORMALE, false);
		bici.setDanneggiata();
		Assertions.assertEquals(bici.isDanneggiata(), true);
	}
	
	/* Questo test verifica che una bicicletta danneggiata venga effettivamente riparata */
	@Test
	public void riparaBicicletta() {
		Bicicletta bici = new Bicicletta("TestID", TipoBicicletta.NORMALE, true);
		bici.riparaBicicletta();
		Assertions.assertEquals(bici.isDanneggiata(), false);
	}
	
	/* Di seguito alcuni test riguardo il calcolo del prezzo del noleggio in base alla durata di quest'ultimo e allo status di studente */
	
	@Test
	public void biciNormaleStudenteTest() {
		Bicicletta bici = new Bicicletta("TestID", TipoBicicletta.NORMALE, false);
		Assertions.assertEquals(bici.calcolaCosto(31, true), 0);
	}
	
	@Test
	public void BiciElettricaStudenteTest() {
		Bicicletta bici = new Bicicletta("TestID", TipoBicicletta.ELETTRICA, false);
		Assertions.assertEquals(bici.calcolaCosto(70, true), 1);
	}
	
	@Test
	public void biciNormaleTest1() {
		Bicicletta bici = new Bicicletta("TestID", TipoBicicletta.NORMALE, false);
		Assertions.assertEquals(bici.calcolaCosto(100, false), 0.5);
	}
	
	@Test
	public void biciNormaleTest2() {
		Bicicletta bici = new Bicicletta("TestID", TipoBicicletta.NORMALE, false);
		Assertions.assertEquals(bici.calcolaCosto(29, false), 0);
	}
	
	@Test
	public void biciElettricaSeggiolinoTest() {
		Bicicletta bici = new Bicicletta("TestID", TipoBicicletta.ELETTRICA_SEGGIOLINO, false);
		Assertions.assertEquals(bici.calcolaCosto(140, false), 4);
	}
}