import dominio.Abbonamento;
import dominio.Bicicletta;
import dominio.TipoBicicletta;
import dominio.Morsa;
import dominio.TipoMorsa;
import dominio.TipoAbbonamento;
import dominio.CartaDiCredito;

public class Main {
    public static void main(String[] args) {
        System.out.println("---Bike Sharing Testing Main---\n"); 
        
        /* BICICLETTE */
        separator("Biciclette");
        Bicicletta b1 = new Bicicletta(TipoBicicletta.NORMALE);
        Bicicletta b2 = new Bicicletta(TipoBicicletta.ELETTRICA);
        Bicicletta b3 = new Bicicletta(TipoBicicletta.ELETTRICA_SEGGIOLINO);

        b1.setDanneggiata(true);
        b3.setDanneggiata(true);
        
        System.out.println(b1.toString());
        System.out.println(b2.toString());
        System.out.println(b3.toString() + "\n");
        
        b1.setDanneggiata(false);
        b2.setDanneggiata(true);
        b3.setDanneggiata(false);
        
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
        CartaDiCredito c1 = new CartaDiCredito("012850003580200", "9/2021", "123");
        CartaDiCredito c2 = new CartaDiCredito("12345678903555", "6/2022", "121");
        System.out.println(c1.toString());
        System.out.println(c2.toString());
        
        
        /* ABBONAMENTI */
        separator("Abbonamenti");
        Abbonamento abb1 = new Abbonamento("DENNISSONE666", "Dennis123", TipoAbbonamento.ANNUALE, c1, true);
        Abbonamento abb2 = new Abbonamento("Dennis23", "Dennis12345", TipoAbbonamento.PERSONALE_SERVIZIO, c2, false);
        Abbonamento abb3 = new Abbonamento("MimmoPetrollini12", "MimmoPetrolla32", TipoAbbonamento.SETTIMANALE, c2, true);
        Abbonamento abb4 = new Abbonamento("GuidoGuidoso903", "GuidoGuinizzelli666", TipoAbbonamento.GIORNALIERO, c1, false);
        
        System.out.println(abb1.toString() + "\n");
        System.out.println(abb2.toString() + "\n");
        System.out.println(abb3.toString() + "\n");
        System.out.println(abb4.toString() + "\n");
    }
    
    
    
    
    
    
    
    
    public static void separator(String text) {
    	System.out.printf("\n------------------------------------------ TEST %s ------------------------------------------\n\n", text);
    }
}