package dati;

import java.util.List;

import dominio.Bicicletta;
import dominio.Morsa;
import dominio.Totem;

/*
 * Riguardo il totem, mi servono diversi metodi che interagiscono col db.
 * 1) Uno che mi recupera tutte le morse del totem presumo collegate anche con le bici, quindi mi serve un join probabilmente
 * 2) Uno che mi permette di aggiungere una morsa e rimuoverla
 * 3) 
 * 
 */

public interface TotemDAO {
	public List<Morsa> getMorse(Totem totem);
	
	
}