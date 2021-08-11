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

public class Main {
    public static void main(String[] args) {
        System.out.println("------------------------------------------ Bike Sharing Testing Main ------------------------------------------\n"); 
        
        /* BICICLETTE */
        separator("Biciclette");
        Bicicletta b1 = new Bicicletta("623", TipoBicicletta.NORMALE);
        Bicicletta b2 = new Bicicletta("121", TipoBicicletta.ELETTRICA);
        Bicicletta b3 = new Bicicletta("532", TipoBicicletta.ELETTRICA_SEGGIOLINO);
        
        // Questi vengono fatti dal totem
        //b1.setDanneggiata(false);
        //b2.setDanneggiata(true);
        //b3.setDanneggiata(false);
        
        System.out.println(b1.toString());
        System.out.println(b2.toString());
        System.out.println(b3.toString());
        
        /* Costi biciclette */
        separator("Costi biciclette");
        System.out.println(b1.calcolaCosto(91, false) + "€");
        System.out.println(b1.calcolaCosto(190, true) + "€");
        System.out.println(b2.calcolaCosto(42, true) + "€");
        System.out.println(b3.calcolaCosto(156, true) + "€");
        
        
        /* MORSE */
        separator("Morse");
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
        
        
        
        /* CARTE DI CREDITO, Valide: 12345678903555 , 012850003580200  */
        separator("Carte di credito");
        CartaDiCredito c1 = new CartaDiCredito("012850003580200", "9/2022", "123");
        CartaDiCredito c2 = new CartaDiCredito("12345678903555", "12/2021", "121");
        System.out.println(c1.toString());
        System.out.println(c2.toString());
        
        
        /* ABBONAMENTI */
        separator("Abbonamenti");
        Abbonamento abb1 = new Abbonamento("DENNISSONE666", "Dennis123", TipoAbbonamento.ANNUALE, c1, true);
        Abbonamento abb2 = new Abbonamento("Dennis23", "Dennis12345", TipoAbbonamento.PERSONALE_SERVIZIO, c2, false);
        Abbonamento abb3 = new Abbonamento("MimmoPetrollini12", "MimmoPetrolla32", TipoAbbonamento.SETTIMANALE, c2, true);
        Abbonamento abb4 = new Abbonamento("GuidoGuidoso903", "GuidoGuinizzelli666", TipoAbbonamento.GIORNALIERO, c1, false);
        
        abb3.attivaAbbonamento();
        
        System.out.println(abb1.toString() + "\n");
        System.out.println(abb2.toString() + "\n");
        System.out.println(abb3.toString() + "\n");
        System.out.println(abb4.toString() + "\n");
        
        
        /* NOLEGGI */
        separator("Noleggi");
        Noleggio n1 = new Noleggio(abb1, b1);
        System.out.println(n1.toString());
        //try { Thread.sleep(5000); } catch(Exception e) { e.printStackTrace(); }
        n1.terminaNoleggio();
        System.out.println("Durata noleggio: " + n1.getDurataNoleggio() + " secondi");
        
        Noleggio n2 = new Noleggio(abb2, b3);
        System.out.println(n2.toString());
        //try { Thread.sleep(3000); } catch(Exception e) { e.printStackTrace(); }
        n2.terminaNoleggio();
        System.out.println("Durata noleggio: " + n2.getDurataNoleggio() + " secondi");
        
        
        /* TOTEM */
        separator("Totem");
        List<Morsa> morse = new ArrayList<Morsa>();
        morse.add(m1);
        morse.add(m2);
        morse.add(m3);
        
        Totem t1 = new Totem("123912", "Via Dennissonis 66", morse);
        t1.restituisciBici(b1);
        t1.restituisciBici(b2);
        t1.restituisciBici(b3);
        t1.comunicaDanni(b1);
        System.out.println(t1.toString());
        // System.out.println(t1.getMorse());
    }
    
    
    
    
    
    
    
    
    public static void separator(String text) {
    	System.out.printf("\n------------------------------------------ TEST %s ------------------------------------------\n\n", text);
    }
}