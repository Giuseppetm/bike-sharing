import dominio.Bicicletta;
import dominio.TipoBicicletta;
import dominio.Morsa;
import dominio.TipoMorsa;
import dominio.StatoMorsa;

public class Main {
    public static void main(String[] args) {
        System.out.println("---Bike Sharing Testing Main---\n"); 
        
        /* BICICLETTE */
        
        /* Test create */
        Bicicletta b1 = new Bicicletta(TipoBicicletta.NORMALE);
        Bicicletta b2 = new Bicicletta(TipoBicicletta.ELETTRICA);
        Bicicletta b3 = new Bicicletta(TipoBicicletta.ELETTRICA_SEGGIOLINO);
        
        /* Test danneggiamento */
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
        System.out.println(b3.toString() + "\n");
        
        /* Test costi */
        System.out.println(b1.calcolaCosto(91, false) + "€");
        System.out.println(b1.calcolaCosto(190, true) + "€");
        System.out.println(b2.calcolaCosto(42, true) + "€");
        System.out.println(b3.calcolaCosto(156, true) + "€" + "\n");
        
        
        /* MORSE */
        
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
    }
}