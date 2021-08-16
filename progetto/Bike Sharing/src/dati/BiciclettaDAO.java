package dati;

import dominio.Bicicletta;
import dominio.TipoBicicletta;
import dominio.Totem;

public interface BiciclettaDAO {
	public void aggiungiBicicletta(Totem totem, TipoBicicletta tipoBicicletta) throws IllegalStateException;
	
	public void rimuoviBicicletta(Totem totem, TipoBicicletta tipoBicicletta);
	
	public void comunicaDanni(Bicicletta bicicletta);
}