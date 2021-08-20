package test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import dominio.Bicicletta;
import dominio.Morsa;
import dominio.TipoBicicletta;
import dominio.TipoMorsa;

public class MorsaTest {
	/* Questo test verifica che venga sollevata un'eccezione IllegalStateException quando si tenta di aprire una morsa senza bicicletta */
	@Test
	public void apriMorsaSenzaBiciclettaTest() {
		Morsa morsa = new Morsa(TipoMorsa.NORMALE);
		Assertions.assertThrows(IllegalStateException.class, () -> { morsa.apri(); });
	}
	
	/* Questo test verifica che non venga sollevata un'eccezione quando si tenta di aprire una morsa cpn una bicicletta disponibile */
	@Test
	public void apriMorsaConBiciclettaTest() {
		Morsa morsa = new Morsa(TipoMorsa.NORMALE);
		Bicicletta bicicletta = new Bicicletta(TipoBicicletta.NORMALE);
		morsa.chiudi(bicicletta);
		Assertions.assertDoesNotThrow(() -> { morsa.apri(); });
	}
	
	
	/* Questo test verifica che venga sollevata un'eccezione IllegalArgumentException quando si tenta di chiudere una morsa senza una bicicletta compatibile */
	@Test
	public void chiudiMorsaBiciclettaNonCompatibileTest() {
		Morsa morsa = new Morsa(TipoMorsa.NORMALE);
		Bicicletta bicicletta = new Bicicletta(TipoBicicletta.ELETTRICA);
		Assertions.assertThrows(IllegalArgumentException.class, () -> { morsa.chiudi(bicicletta); });
	}
	
	/* Questo test verifica che non venga sollevata un'eccezione quando si tenta di chiudere una morsa con una bicicletta compatibile */
	@Test
	public void chiudiMorsaConBiciclettaCompatibileTest() {
		Morsa morsa = new Morsa(TipoMorsa.NORMALE);
		Bicicletta bicicletta = new Bicicletta(TipoBicicletta.NORMALE);
		Assertions.assertDoesNotThrow(() -> { morsa.chiudi(bicicletta); });
	}
}