package dati;

import dominio.Bicicletta;
import dominio.TipoBicicletta;
import dominio.Totem;

public class BiciclettaDAOPostgres implements BiciclettaDAO {
	private ConnessioneDb connessioneDb;
	
	public BiciclettaDAOPostgres() {
		this.connessioneDb = ConnessioneDb.getIstance();
	}
	
	@Override
	public void aggiungiBicicletta(Totem totem, TipoBicicletta tipoBicicletta) {
		// Qui devi capire se c'è spazio, idem careful alle eccezioni
	}
	
	@Override
	public void rimuoviBicicletta(Totem totem, TipoBicicletta tipoBicicletta) {
		// Qui devi controllare se c'è il tipo di bicicletta per rimuoverla
	}
	
	@Override
	public void comunicaDanni(Bicicletta bicicletta) {
		
	}
}