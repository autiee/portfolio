/**
 * 
 */
package joukkuerekisteri;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

/**
 * joukkueet ja pelaajat -luokkien välinen yhteistyö ja niiden tietojen välittäminen pyydettäessä
 * lukee ja kirjoittaa joukkuerekisteri-tiedostoon pyytämällä apua avustajiltaan
 * @author eelia
 * @version 4.8.2020
 *
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import joukkuerekisteri.SailoException;
 *  private Joukkuerekisteri joukkuerekisteri;
 *  private Joukkue j1;
 *  private Joukkue j2;
 *  private int jid1;
 *  private int jid2;
 *  private Pelaaja p1;
 *  private Pelaaja p2;
 *  private Pelaaja p3; 
 *  private Pelaaja p4; 
 *  private Pelaaja p5;
 *  
 *  
 *  public void alustaJoukkuerekisteri() {
 *    joukkuerekisteri = new Joukkuerekisteri();
 *    j1 = new Joukkue(); j1.rekisteroi();
 *    j2 = new Joukkue(); j2.rekisteroi();
 *    jid1 = j1.getTunnusNro();
 *    jid2 = j2.getTunnusNro();
 *    p1 = new Pelaaja(); p1.rekisteroi(); p1.setJoukkueenro(jid2);
 *    p2 = new Pelaaja(); p2.rekisteroi(); p2.setJoukkueenro(jid1);
 *    p3 = new Pelaaja(); p3.rekisteroi(); p3.setJoukkueenro(jid2); 
 *    p4 = new Pelaaja(); p4.rekisteroi(); p4.setJoukkueenro(jid1); 
 *    p5 = new Pelaaja(); p5.rekisteroi(); p5.setJoukkueenro(jid2);
 *    try {
 *    joukkuerekisteri.lisaa(j1);
 *    joukkuerekisteri.lisaa(j2);
 *    joukkuerekisteri.lisaa(p1);
 *    joukkuerekisteri.lisaa(p2);
 *    joukkuerekisteri.lisaa(p3);
 *    joukkuerekisteri.lisaa(p4);
 *    joukkuerekisteri.lisaa(p5);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
 */
public class Joukkuerekisteri {
    private Joukkueet joukkueet = new Joukkueet();
    private Pelaajat pelaajat = new Pelaajat();
    private DecimalFormat df = new DecimalFormat("0.00");


    /**
     * Palauttaa sarjan joukkueiden määrän
     * @return joukkueiden määrän
     */
    public int getJoukkueita() {
        return joukkueet.getLkm();
    }
    
    
    /**
     * Palauttaa sarjan pelaajien määrän
     * @return pelaajien määrä
     */
    public int getPelaajia() {
        return pelaajat.getLkm();
    }
    
    
    /**
     * Palauttaa sarjan pelaajien keskipainon
     * @return pelaajien keskipaino
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   p1.setPaino("100.00");
     *   p2.setPaino("95.00");
     *   p3.setPaino("80.25");
     *   p4.setPaino("80.50");
     *   p5.setPaino("50.75");
     *   joukkuerekisteri.getPaino() === "81,30";
     *   p1.setPaino("70.40");
     *   joukkuerekisteri.getPaino() === "75,38";
     * </pre>
     */
    public String getPaino() {
        double summa = 0;
        List<Pelaaja> pel = pelaajat.getListaPelaajista();
        for (Pelaaja p : pel) {
            summa += p.getPaino();
        }
        if(summa == 0) return "0";
        return String.valueOf(df.format(summa/pel.size()));
    }
    
    
    /**
     * Palauttaa sarjan pelaajien keskipituuden
     * @return pelaajien keskipituus
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   p1.setPituus("180.50");
     *   p2.setPituus("195.00");
     *   p3.setPituus("183.25");
     *   p4.setPituus("170.50");
     *   p5.setPituus("175.75");
     *   joukkuerekisteri.getPituus() === "181,00";
     *   p1.setPituus("182.00");
     *   joukkuerekisteri.getPituus() === "181,30";
     * </pre>
     */
    public String getPituus() {
        double summa = 0;
        List<Pelaaja> pel = pelaajat.getListaPelaajista();
        for (Pelaaja p : pel) {
            summa += p.getPituus();
        }
        if(summa == 0) return "0";
        return String.valueOf(df.format(summa/pel.size()));
    }
    
    
    /**
     * Palauttaa sarjan pelaajien keski-ikä
     * @return pelaajien keski-ikä
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   p1.setSyntyma("01:01:2000");
     *   p2.setSyntyma("01:01:1995");
     *   p3.setSyntyma("01:01:1990");
     *   p4.setSyntyma("01:01:2005");
     *   p5.setSyntyma("01:01:1990");
     *   joukkuerekisteri.getIka() === "24,00";
     *   p1.setSyntyma("01:01:1994");
     *   joukkuerekisteri.getIka() === "25,20";
     * </pre>
     */
    public String getIka() {
        double summa = 0;
        List<Pelaaja> pel = pelaajat.getListaPelaajista();
        for (Pelaaja p : pel) {
            summa += p.getIka();
        }
        if(summa == 0) return "0";
        return String.valueOf(df.format(summa/pel.size()));
    }
    
    
    /**
     * Palauttaa pelipaikalla pelaavien pelaajien määrä
     * @param pelipaikka haluttu pelipaikka
     * @return pelipaikalla pelaavien pelaajien määrä
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   joukkuerekisteri.getPelipaikkaMaara("Hyökkääjä") === "0.0";
     *   p2.setPelipaikka("Hyökkääjä");
     *   p4.setPelipaikka("Hyökkääjä");
     *   joukkuerekisteri.getPelipaikkaMaara("Hyökkääjä") === "2.0";
     *   p2.setPelipaikka("Puolustaja");
     *   joukkuerekisteri.getPelipaikkaMaara("Hyökkääjä") === "1.0";
     * </pre>
     */
    public String getPelipaikkaMaara(String pelipaikka) {
        double summa = 0;
        List<Pelaaja> pel = pelaajat.getListaPelaajista();
        for (Pelaaja p : pel) {
            if(p.getPelipaikka().equals(pelipaikka))
            summa++;
        }
        return String.valueOf(summa);
    }
     
     
    /**
     * Palauttaa joukkueen pelaajien pituuksien keskiarvon
     * @param joukkue käytettävä joukkue
     * @return joukkueen keskipituus
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   p2.setPituus("180.00");
     *   p4.setPituus("190.00");
     *   joukkuerekisteri.KPituus(j1) === "185,00";
     *   p2.setPituus("175.00");
     *   joukkuerekisteri.KPituus(j1) === "182,50";
     * </pre>
     */
    public String KPituus(Joukkue joukkue) {
        double summa = 0;
        List<Pelaaja> pel = annaPelaajat(joukkue);
        for (Pelaaja p : pel) {
            summa += p.getPituus();
        }
        if(summa == 0) return "0";
        return String.valueOf(df.format(summa/pel.size()));
    }
     
     
    /**
     * Palauttaa joukkueen pelaajien painojen keskiarvon
     * @param joukkue käytettävä joukkue
     * @return joukkueen keskipaino
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   p2.setPaino("80.00");
     *   p4.setPaino("100.00");
     *   joukkuerekisteri.KPaino(j1) === "90,00";
     *   p2.setPaino("95.00");
     *   joukkuerekisteri.KPaino(j1) === "97,50";
     * </pre>
     */
    public String KPaino(Joukkue joukkue) {
        double summa = 0;
        List<Pelaaja> pel = annaPelaajat(joukkue);
        for (Pelaaja p : pel) {
            summa += p.getPaino();
        }
        if(summa == 0) return "0";
        return String.valueOf(df.format(summa/pel.size()));
    }
     
     
    /**
     * Palauttaa joukkueen pelaajien keski-iän
     * @param joukkue käytettävä joukkue
     * @return joukkueen keski-ikä
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   p2.setSyntyma("01:01:2000");
     *   p4.setSyntyma("01:01:1990");
     *   joukkuerekisteri.KIka(j1) === "25,00";
     *   p2.setSyntyma("01:01:1994");
     *   joukkuerekisteri.KIka(j1) === "28,00";
     * </pre>
     */
    public String KIka(Joukkue joukkue) {
        double summa = 0;
        List<Pelaaja> pel = annaPelaajat(joukkue);
        for (Pelaaja p : pel) {
            summa += Double.valueOf(p.getIka());
        }
        if(summa == 0) return "0";
        return String.valueOf(df.format(summa/pel.size()));
    }

    
    /**
     * Poistaa joukkueen ja sen pelaajat 
     * @param joukkue poistettava joukkue
     * @return montako joukkuetta poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   joukkuerekisteri.getJoukkueita() === 2;
     *   joukkuerekisteri.annaPelaajat(j1).size() === 2;
     *   joukkuerekisteri.poista(j1) === 1;
     *   joukkuerekisteri.getJoukkueita() === 1;
     *   joukkuerekisteri.annaPelaajat(j1).size() === 0;
     * </pre>
     */
    public int poista(Joukkue joukkue) {
        if ( joukkue == null ) return 0;
        int ret = joukkueet.poista(joukkue.getTunnusNro()); 
        pelaajat.poistaJoukkueenPelaajat(joukkue.getTunnusNro()); 
        return ret;
    }
    
    
    /**
     * Poistaa pelaajan 
     * @param pelaaja poistettava pelaaja
     * @return montako pelaaja poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   joukkuerekisteri.annaPelaajat(j1).size() === 2;
     *   joukkuerekisteri.poistaPelaaja(p2) === 1;
     *   joukkuerekisteri.annaPelaajat(j1).size() === 1;
     * </pre>
     */
    public int poistaPelaaja(Pelaaja pelaaja) {
        if ( pelaaja == null ) return 0;
        int ret = pelaajat.poista(pelaaja.getTunnusNro());; 
        return ret;
    }

    
    /**
     * Lisää joukkueen rekisteriin 
     * @param joukkue lisättävä joukkue
     * @throws SailoException jos ei voi lisätä
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   joukkuerekisteri.getJoukkueita() === 2;
     *   Joukkue j3 = new Joukkue(); j3.rekisteroi();
     *   joukkuerekisteri.lisaa(j3);
     *   joukkuerekisteri.getJoukkueita() === 3;
     * </pre>
     */
    public void lisaa(Joukkue joukkue) throws SailoException {
        joukkueet.lisaa(joukkue);
    }
    
    
    /**
     * Lisää pelaajan rekisteriin 
     * @param pelaaja lisättävä pelaaja
     * @throws SailoException jos ei voi lisätä
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaJoukkuerekisteri();
     *   joukkuerekisteri.getPelaajia() === 5;
     *   Pelaaja p6 = new Pelaaja(); p6.rekisteroi();
     *   joukkuerekisteri.lisaa(p6);
     *   joukkuerekisteri.getPelaajia() === 6;
     * </pre>
     */
    public void lisaa(Pelaaja pelaaja) throws SailoException {
        pelaajat.lisaa(pelaaja);
    }

    
    /** 
     * Korvaa joukkueen tietorakenteessa.  Ottaa joukkueen omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva joukkue.  Jos ei löydy, 
     * niin lisätään uutena joukkueena. 
     * @param joukkue lisättävän joukkueen viite. 
     * @throws SailoException jos tietorakenne on jo täynnä 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *  alustaJoukkuerekisteri();  
     *  joukkuerekisteri.getJoukkueita() === 2;
     *  joukkuerekisteri.korvaaTaiLisaa(j2);
     *  joukkuerekisteri.getJoukkueita() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Joukkue joukkue) throws SailoException { 
        joukkueet.korvaaTaiLisaa(joukkue); 
    }
    
    
    /** 
     * Korvaa pelaajan tietorakenteessa.  Ottaa pelaajan omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva pelaaja.  Jos ei löydy, 
     * niin lisätään uutena pelaajana. 
     * @param pelaaja lisättävän pelaajan viite. 
     * @throws SailoException jos tietorakenne on jo täynnä 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *  alustaJoukkuerekisteri();  
     *  joukkuerekisteri.getPelaajia() === 5;
     *  joukkuerekisteri.korvaaTaiLisaa(p2);
     *  joukkuerekisteri.getPelaajia() === 5;
     * </pre>
     */
    public void korvaaTaiLisaa(Pelaaja pelaaja) throws SailoException { 
        pelaajat.korvaaTaiLisaa(pelaaja); 
    }
    

    /**
     * @param i etsittävän joukkueen paikka
     * @return paikalla oleva joukkue
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Joukkue annaJoukkue(int i) throws IndexOutOfBoundsException {
        return joukkueet.anna(i);
    }
    
    
    /**
     * Haetaan kaikki joukkueen pelaajat
     * @param joukkue joukkue jonka pelaajia haetaan
     * @return lista joukkueen pelaajista
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     * 
     *  alustaJoukkuerekisteri();
     *  Joukkue j3 = new Joukkue();
     *  j3.rekisteroi();
     *  joukkuerekisteri.lisaa(j3);
     *  
     *  List<Pelaaja> loytyneet;
     *  loytyneet = joukkuerekisteri.annaPelaajat(j3);
     *  loytyneet.size() === 0; 
     *  loytyneet = joukkuerekisteri.annaPelaajat(j1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == p2 === true;
     *  loytyneet.get(1) == p4 === true;
     *  loytyneet = joukkuerekisteri.annaPelaajat(j2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == p1 === true;
     * </pre> 
     */
    public List<Pelaaja> annaPelaajat(Joukkue joukkue) {
        return pelaajat.annaPelaajat(joukkue.getTunnusNro());
    }


    /**
     * Haetaan kaikki pelaajat
     * @return lista pelaajista
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     * 
     *  alustaJoukkuerekisteri();
     *  List<Pelaaja> loytyneet = joukkuerekisteri.getListaPelaajista();
     *  loytyneet.size() === 5; 
     *  loytyneet.get(0) == p1 === true;
     *  loytyneet.get(1) == p2 === true;
     *  loytyneet.get(2) == p3 === true;
     * </pre> 
     */
    public List<Pelaaja> getListaPelaajista() {
        return pelaajat.getListaPelaajista();
    }
    
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien joukkueiden viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenne löytyneistä joukkueista 
     * @example 
     * <pre name="test">
     *   #THROWS CloneNotSupportedException, SailoException
     *   alustaJoukkuerekisteri();
     *   Joukkue j3 = new Joukkue(); j3.rekisteroi();
     *   j3.setNimi("FC Honkola");
     *   joukkuerekisteri.lisaa(j3);
     *   Collection<Joukkue> loytyneet = joukkuerekisteri.etsi("*FC*",0);
     *   loytyneet.size() === 1;
     *   Iterator<Joukkue> it = loytyneet.iterator();
     *   it.next() == j3 === true; 
     * </pre>
     */
    public Collection<Joukkue> etsi(String hakuehto, int k) { 
        return joukkueet.etsi(hakuehto, k); 
    }
    

    /**
     * Lukee sarjan tiedot tiedostosta
     * @param nimi luettavan tiedoston nimi
     * @throws SailoException jos ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *   
     *  String hakemisto = "testisarja";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/joukkueet.dat");
     *  File fhtied = new File(hakemisto+"/pelaajat.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  fhtied.delete();
     *  joukkuerekisteri = new Joukkuerekisteri(); 
     *  joukkuerekisteri.lueTiedostosta(hakemisto); #THROWS SailoException
     *  alustaJoukkuerekisteri();
     *  joukkuerekisteri.setTiedosto(hakemisto);
     *  joukkuerekisteri.tallenna(); 
     *  joukkuerekisteri = new Joukkuerekisteri();
     *  joukkuerekisteri.lueTiedostosta(hakemisto);
     *  Collection<Joukkue> kaikki = joukkuerekisteri.etsi("",-1); 
     *  Iterator<Joukkue> it = kaikki.iterator();
     *  it.next() === j1;
     *  it.next() === j2;
     *  it.hasNext() === false;
     *  List<Pelaaja> loytyneet = joukkuerekisteri.annaPelaajat(j1);
     *  Iterator<Pelaaja> ip = loytyneet.iterator();
     *  ip.next() === p2;
     *  ip.next() === p4;
     *  ip.hasNext() === false;
     *  loytyneet = joukkuerekisteri.annaPelaajat(j2);
     *  ip = loytyneet.iterator();
     *  ip.next() === p1;
     *  ip.next() === p3;
     *  ip.next() === p5;
     *  ip.hasNext() === false;
     *  joukkuerekisteri.lisaa(j2);
     *  joukkuerekisteri.lisaa(p5);
     *  joukkuerekisteri.tallenna();
     *  ftied.delete()  === true;
     *  fhtied.delete() === true;
     *  File fbak = new File(hakemisto+"/joukkueet.bak");
     *  File fhbak = new File(hakemisto+"/pelaajat.bak");
     *  fbak.delete() === true;
     *  fhbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        joukkueet = new Joukkueet(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        pelaajat = new Pelaajat();
         
        setTiedosto(nimi);
        joukkueet.lueTiedostosta();
        pelaajat.lueTiedostosta();
    }


    /**
     * Tallentaa tiedot tiedostoon
     * @throws SailoException jos tulee ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
                joukkueet.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        } 
        try {
            pelaajat.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);     
    }

    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        joukkueet.setTiedostonPerusNimi(hakemistonNimi + "joukkueet");
        pelaajat.setTiedostonPerusNimi(hakemistonNimi + "pelaajat");
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Joukkuerekisteri joukkuerekisteri = new Joukkuerekisteri();
        
        try {
        
        Joukkue tiimi = new Joukkue();
        tiimi.rekisteroi();
        //tiimi.taytaTiedot();
        Joukkue tiimi2 = new Joukkue();
        tiimi2.rekisteroi();
        //tiimi2.taytaTiedot();
        
        joukkuerekisteri.lisaa(tiimi);
        joukkuerekisteri.lisaa(tiimi2);
        
        int id = tiimi.getTunnusNro();
        Pelaaja pelaaja1 = new Pelaaja(id);
        //pelaaja1.taytaTiedot(id);
        joukkuerekisteri.lisaa(pelaaja1);
        
        System.out.println("============= Joukkuerekisterin testi =================");

        for (int i = 0; i < joukkuerekisteri.getJoukkueita(); i++) {
            Joukkue joukkue = joukkuerekisteri.annaJoukkue(i);
            System.out.println("Joukkue paikassa: " + i);
            joukkue.tulosta(System.out);
            List<Pelaaja> loytyneet = joukkuerekisteri.annaPelaajat(joukkue);
            for (Pelaaja pelaaja : loytyneet)
                pelaaja.tulosta(System.out);
        }
        
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
