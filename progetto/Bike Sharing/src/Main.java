import java.util.ArrayList;
import java.util.List;

import dominio.Abbonamento;
import dominio.Bicicletta;
import dominio.TipoBicicletta;
import dominio.Morsa;
import dominio.TipoMorsa;
import dominio.TipoAbbonamento;
import dominio.CartaDiCredito;
import dominio.Noleggio;
import dominio.Totem;
import dati.AbbonamentoDAOPostgres;
import dati.BiciclettaDAOPostgres;
import dati.ConnessioneDb;
import dati.DatiStatisticiDAOPostgres;
import dati.MorsaDAOPostgres;
import dati.NoleggioDAOPostgres;
import dati.TotemDAOPostgres;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------------------------------------ Bike Sharing Testing Main ------------------------------------------\n"); 
        
        /* BICICLETTE */
        //separator("Biciclette");
        /*
        Bicicletta b1 = new Bicicletta(TipoBicicletta.NORMALE);
        Bicicletta b2 = new Bicicletta(TipoBicicletta.ELETTRICA);
        Bicicletta b3 = new Bicicletta(TipoBicicletta.ELETTRICA_SEGGIOLINO);
        */
        
        // Questi vengono fatti dal totem
        //b1.setDanneggiata(false);
        //b2.setDanneggiata(true);
        //b3.setDanneggiata(false);
        
        /*
        System.out.println(b1.toString());
        System.out.println(b2.toString());
        System.out.println(b3.toString());
        */
        
        /* Costi biciclette */
        //separator("Costi biciclette");
        /*
        System.out.println(b1.calcolaCosto(91, false) + "€");
        System.out.println(b1.calcolaCosto(190, true) + "€");
        System.out.println(b2.calcolaCosto(42, true) + "€");
        System.out.println(b3.calcolaCosto(156, true) + "€");
        */
        
        
        /* MORSE */
        //separator("Morse");
        /*
        Morsa m1 = new Morsa(TipoMorsa.ELETTRICA);
        m1.chiudi(b2);
        
        Morsa m2 = new Morsa(TipoMorsa.NORMALE);
        m2.chiudi(b1);
        
        Morsa m3 = new Morsa(TipoMorsa.ELETTRICA);
        m3.chiudi(b3);
        
        System.out.println(m1.toString());
        System.out.println(m2.toString());
        System.out.println(m3.toString());
        
        b2 = m1.apri();
        b1 = m2.apri();
        b3 = m3.apri();
        
        System.out.println(m1.toString());
        System.out.println(m2.toString());
        System.out.println(m3.toString());
        */
        
        
        
        /* CARTE DI CREDITO, Valide: 12345678903555 , 012850003580200  */
        //separator("Carte di credito");
        CartaDiCredito c1 = new CartaDiCredito("4079029256061430", "9/2024", "242");
        /*
        CartaDiCredito c2 = new CartaDiCredito("12345678903555", "12/2021", "121");
        CartaDiCredito c3 = new CartaDiCredito("4475794209397914", "12/2023", "721");
        CartaDiCredito c4 = new CartaDiCredito("4291790808416693", "12/2021", "118");
        CartaDiCredito c5 = new CartaDiCredito("4870709574110347", "12/2021", "523");
        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c3.toString());
        System.out.println(c4.toString());
        */
        
        
        /* ABBONAMENTI */
        //separator("Abbonamenti");
        Abbonamento abb1 = new Abbonamento("MimmolasPetrollas023", TipoAbbonamento.SETTIMANALE, c1, true);
        /*
        Abbonamento abb2 = new Abbonamento("Dennis12345", TipoAbbonamento.PERSONALE_SERVIZIO, c2, false);
        Abbonamento abb3 = new Abbonamento("MimmoPetrolla32", TipoAbbonamento.SETTIMANALE, c3, true);
        Abbonamento abb4 = new Abbonamento("GuidoGuinizzelli666", TipoAbbonamento.GIORNALIERO, c4, false);
        Abbonamento abb5 = new Abbonamento("GiuseppeOfField214", TipoAbbonamento.GIORNALIERO, c5, false);
        
        abb3.attivaAbbonamento();
        
        System.out.println(abb1.toString() + "\n");
        System.out.println(abb2.toString() + "\n");
        System.out.println(abb3.toString() + "\n");
        System.out.println(abb4.toString() + "\n");
        */
        
        
        
        /* TOTEM */
        separator("Totem");
        /*
	        List<Morsa> morse = new ArrayList<Morsa>();
	        morse.add(m1);
	        morse.add(m2);
	        morse.add(m3);
	        Totem t1 = new Totem("Via Dennissonis 66", morse);
	        System.out.println(t1.getMorse());
        */
        
        Totem t1 = new Totem("Via Dennissonis 66");
        Totem t2 = new Totem("Via De Guidonis 32");
        System.out.println(t1.toString());
        System.out.println(t2.toString());
        
        /*
        t1.aggiungiMorsa(m1);
        t1.aggiungiMorsa(m2);
        t1.aggiungiMorsa(m3);
        t1.restituisciBicicletta(b1);
        t1.restituisciBicicletta(b2);
        t1.restituisciBicicletta(b3);
        t1.comunicaDanni(b1);
        t1.aggiungiMorsaByTipo(TipoMorsa.ELETTRICA);
        t1.aggiungiMorsaByTipo(TipoMorsa.NORMALE);
        t1.aggiungiMorsaByTipo(TipoMorsa.ELETTRICA);
        t1.aggiungiMorsaByTipo(TipoMorsa.ELETTRICA);
        */
        
        
        /* NOLEGGI */
        //separator("Noleggi");
        /*
        Noleggio n1 = new Noleggio(abb1, b1, t1);
        System.out.println(n1.toString());
        //try { Thread.sleep(5000); } catch(Exception e) { e.printStackTrace(); }
        n1.terminaNoleggio();
        System.out.println("Durata noleggio: " + n1.getDurataNoleggio() + " minuti\n");
        
        Noleggio n2 = new Noleggio(abb2, b3, t2);
        System.out.println(n2.toString());
        //try { Thread.sleep(3000); } catch(Exception e) { e.printStackTrace(); }
        n2.terminaNoleggio();
        System.out.println("Durata noleggio: " + n2.getDurataNoleggio() + " minuti\n");
        */

        
        /* CONNESSIONE AL DB */
        separator("Connessioni al database");
        ConnessioneDb db = ConnessioneDb.getIstance();
        try {
        	db.connetti();
        	System.out.println("@@@ Connesso al database PostgreSQL! @@@\n");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        AbbonamentoDAOPostgres abbonamentoDao = new AbbonamentoDAOPostgres();
        //abbonamentoDao.registraAbbonamento(abb1);
        //abbonamentoDao.registraAbbonamento(abb2);
        //abbonamentoDao.registraAbbonamento(abb3);
        //abbonamentoDao.registraAbbonamento(abb4);
        //abbonamentoDao.registraAbbonamento(abb5);
        

        /* Stampa abbonamenti */
        System.out.println("---Lista abbonamenti presenti sul db---");
        List<Abbonamento> abbonamenti = new ArrayList<Abbonamento>();
        try {
        	 abbonamenti = abbonamentoDao.getListaAbbonamenti();
        	for (Abbonamento abb : abbonamenti)
        		System.out.println(abb.toString() + "\n");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        
        Abbonamento abbLogin = abbonamentoDao.effettuaLogin("dd3a0289-960e-44e1-9bcb-3d7a1721b36f", "MimmolasPetrollas023");
        System.out.println("\n---Abbonamento retrieve dal login---\n" + abbLogin.toString());
        
        // abbonamentoDao.ammonisciAbbonamento(abbLogin);
        // abbonamentoDao.ammonisciAbbonamento(abbLogin);
        
        // Abbonamento abbLogin2 = abbonamentoDao.effettuaLogin("45d05b0e-412c-4975-8bdb-de5088c1dd17", "GuidoGuinizzelli666");
        // System.out.println("\n---Abbonamento retrieve dal login n.2 BEFORE ATTIVAZIONE---\n" + abbLogin2.toString());
        // abbonamentoDao.attivaAbbonamento(abbLogin2);
        // System.out.println("\n---Abbonamento retrieve dal login n.2 AFTER ATTIVAZIONE---\n" + abbLogin2.toString());
        
        
        /* Stampa totem */
        System.out.println("\n---Lista totem presenti sul db---");
        TotemDAOPostgres totemDao = new TotemDAOPostgres();
        //totemDao.aggiungiTotem(t1);
        //totemDao.rimuoviTotem(t1);
        //totemDao.aggiungiTotem(t2);
        
    	List<Totem> totems = new ArrayList<Totem>();
    	try {
    		totems = totemDao.getListaTotem();
    	} catch (Exception e) {e.printStackTrace();}
    	for (Totem t : totems) System.out.println(t);
        
        MorsaDAOPostgres morsaDao = new MorsaDAOPostgres();
        //morsaDao.aggiungiMorsa(totems.get(0), TipoMorsa.ELETTRICA);
        //morsaDao.aggiungiMorsa(totems.get(0), TipoMorsa.NORMALE);
        
        //morsaDao.rimuoviMorsa(totems.get(0), TipoMorsa.NORMALE);
        //morsaDao.rimuoviMorsa(totems.get(0), TipoMorsa.ELETTRICA);
        
        //morsaDao.aggiungiMorsa(totems.get(1), TipoMorsa.NORMALE);
        //morsaDao.aggiungiMorsa(totems.get(1), TipoMorsa.ELETTRICA);
        
        BiciclettaDAOPostgres biciclettaDao = new BiciclettaDAOPostgres();
        
        //biciclettaDao.aggiungiBicicletta(totems.get(0), TipoBicicletta.ELETTRICA);
        //biciclettaDao.aggiungiBicicletta(totems.get(0), TipoBicicletta.NORMALE);
        //biciclettaDao.aggiungiBicicletta(totems.get(1), TipoBicicletta.NORMALE);

        //biciclettaDao.rimuoviBicicletta(totems.get(1), TipoBicicletta.ELETTRICA);
        
        //biciclettaDao.comunicaDanni(new Bicicletta("43500881-3f1d-48ac-b183-760ab3e7532d", TipoBicicletta.NORMALE, false));
        //biciclettaDao.riparaBicicletta(new Bicicletta("43500881-3f1d-48ac-b183-760ab3e7532d", TipoBicicletta.NORMALE, false));
        
        
        
        
        /* Noleggi */
        NoleggioDAOPostgres noleggioDao = new NoleggioDAOPostgres();
        
        
        //noleggioDao.iniziaNoleggio(abbLogin, totems.get(0), TipoBicicletta.ELETTRICA);
        //noleggioDao.finisciNoleggio(abbLogin, totems.get(0)); // La cosa figa è che puoi consegnarlo ad un altro totem volendo
        
        /*
        List<Noleggio> noleggi = noleggioDao.getListaNoleggi(abbLogin);
        System.out.printf("\n---Lista noleggi dell'abbonamento %s---\n", abbLogin.getCodice());
        for (Noleggio noleggio : noleggi) System.out.println(noleggio + "\n");
        */
        
        
        
        
        
        
        
        
        
        /* Dati statistici */
        /*
        System.out.println("---Calcolo dati statistici---");
        DatiStatisticiDAOPostgres datiStatisticiDao = new DatiStatisticiDAOPostgres();
        System.out.println(datiStatisticiDao.getNumeroAbbonamenti());
        System.out.println(datiStatisticiDao.getNumeroAbbonamentiAttivi());
        System.out.println(datiStatisticiDao.getNumeroAbbonamentiSospesi());
        System.out.println(datiStatisticiDao.getNumeroBicicletteDanneggiate());
        System.out.println(datiStatisticiDao.getNumeroNoleggiEffettuati());
        System.out.println(datiStatisticiDao.getNumeroTotem());
        try { System.out.println(datiStatisticiDao.getTotemPiùUtilizzato()); } catch(Exception e) {}
        */
    }
    
    public static void separator(String text) {
    	System.out.printf("\n------------------------------------------ TEST %s ------------------------------------------\n\n", text);
    }
}