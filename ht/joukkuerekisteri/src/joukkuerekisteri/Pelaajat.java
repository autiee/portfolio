package joukkuerekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 
 * @author eelia
 * @version 4.8.2020
 *
 */
public class Pelaajat implements Iterable<Pelaaja> {
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";

    private final List<Pelaaja> alkiot = new ArrayList<Pelaaja>();
      

    /**
     * Pelaajien alustaminen
     */
    public Pelaajat() {
        //
    }
  

    /** Lis‰‰ pelaajan tietorakenteeseen
     * @param pelaaja Lis‰tt‰v‰ pelaaja
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja p1 = new Pelaaja(), p2 = new Pelaaja();
     * pelaajat.getLkm() === 0;
     * pelaajat.lisaa(p1); pelaajat.getLkm() === 1;
     * pelaajat.lisaa(p2); pelaajat.getLkm() === 2;
     * pelaajat.lisaa(p1); pelaajat.getLkm() === 3;
     * Iterator<Pelaaja> it = pelaajat.iterator(); 
     * it.next() === p1;
     * it.next() === p2; 
     * it.next() === p1;  
     * pelaajat.lisaa(p1); pelaajat.getLkm() === 4;
     * pelaajat.lisaa(p1); pelaajat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Pelaaja pelaaja) {
        alkiot.add(pelaaja);
        muutettu = true;
    }
    
    
    /**
     * Korvaa alkuper‰isen pelaajan muokatulla pelaajalla tai lis‰‰ sen tietorakenteeseen
     * @param pelaaja korvattava tai lis‰tt‰v‰ pelaaja
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja p1 = new Pelaaja(), p2 = new Pelaaja();
     * p1.rekisteroi(); p2.rekisteroi();
     * pelaajat.getLkm() === 0;
     * pelaajat.korvaaTaiLisaa(p1); pelaajat.getLkm() === 1;
     * pelaajat.korvaaTaiLisaa(p2); pelaajat.getLkm() === 2;
     * Pelaaja p3 = p1.clone();
     * pelaajat.korvaaTaiLisaa(p3); pelaajat.getLkm() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Pelaaja pelaaja) {
        int id = pelaaja.getTunnusNro();
        for (int i = 0; i < getLkm(); i++) {
            if (alkiot.get(i).getTunnusNro() == id) {
            alkiot.set(i, pelaaja);
            muutettu = true;
            return;
            }
        }
        lisaa(pelaaja);
    }
    
    
    /**
     * Poistaa poistettavan joukkueen pelaajat
     * @param tunnusNro joukkueen tunnusnumero
     * @return poistettujen pelaajien m‰‰r‰
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja p1 = new Pelaaja(), p2 = new Pelaaja(), p3 = new Pelaaja();
     * p1.rekisteroi(); p2.rekisteroi(); p3.rekisteroi(); 
     * p1.setJoukkueenro(1); p2.setJoukkueenro(1); p3.setJoukkueenro(1);
     * pelaajat.lisaa(p1); pelaajat.lisaa(p2); pelaajat.lisaa(p3);
     * pelaajat.getLkm() === 3;
     * pelaajat.poistaJoukkueenPelaajat(1); pelaajat.getLkm() === 0; 
     * </pre>
     */
    public int poistaJoukkueenPelaajat(int tunnusNro) {
        int n = 0;
        for (Iterator<Pelaaja> it = alkiot.iterator(); it.hasNext();) {
            Pelaaja pelaaja = it.next();
            if ( pelaaja.getJoukkueNro() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
     
    /**
     * Poistaa valitun pelaajan
     * @param id poistettavan pelaajan tunnusnumero
     * @return poistettujen pelaajien m‰‰r‰
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja p1 = new Pelaaja(), p2 = new Pelaaja(), p3 = new Pelaaja();
     * p1.rekisteroi(); p2.rekisteroi(); p3.rekisteroi(); 
     * int id1 = p1.getTunnusNro(); 
     * pelaajat.lisaa(p1); pelaajat.lisaa(p2); pelaajat.lisaa(p3);
     * pelaajat.getLkm() === 3;
     * pelaajat.poista(id1); pelaajat.getLkm() === 2;
     * pelaajat.poista(id1+3); pelaajat.getLkm() === 2; 
     * </pre>
     */
    public int poista(int id) {
        int n = 0;
        for (Iterator<Pelaaja> it = alkiot.iterator(); it.hasNext();) {
            Pelaaja pelaaja = it.next();
            if ( pelaaja.getTunnusNro() == id ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    

    /** Lukee pelaajat tiedostosta
     * @param tied Tiedoston nimen alkuosa
     * @throws SailoException Lukemisen ep‰onnistuessa
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                    Pelaaja pelaaja = new Pelaaja();
                    pelaaja.parse(rivi); // voisi olla virhek‰sittely
                    lisaa(pelaaja);
             }
             muutettu = false;
        
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
        
            
    /**
     * Luetaan aikaisemmin annetun nimisest‰ tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /** 
     * Tallentaa pelaajat tiedostoon
     * @throws SailoException jos tallennus ep‰onnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Pelaaja pelaaja : this) {
                fo.println(pelaaja.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }


    /** 
     * Palauttaa pelaajien lukum‰‰r‰n
     * @return pelaajien lukum‰‰r‰
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Palauttaa pelaajat listana
     * @return pelaajat listana
     */
    public List<Pelaaja> getListaPelaajista() {
        return alkiot;
    }
    
    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }
    
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    
    /**
     * Iteraattori kaikkien pelaajien l‰pik‰ymiseen
     * return pelaajaiteraattori
     */
    @Override
    public Iterator<Pelaaja> iterator() {
        return alkiot.iterator();
    }


    /** 
     * Palauttaa tietyn joukkueen pelaajat
     * @param tunnusnro Joukkueen tunnusnumero
     * @return joukkueen pelaajat
     */
    public List<Pelaaja> annaPelaajat(int tunnusnro) {
        List<Pelaaja> loydetyt = new ArrayList<Pelaaja>();
        for (Pelaaja pelaaja : alkiot)
            if (pelaaja.getJoukkueNro() == tunnusnro) loydetyt.add(pelaaja);
        return loydetyt;
    }


    /** 
     * Luokan testaus
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Pelaajat pelaajat = new Pelaajat();
        Pelaaja pelaaja1 = new Pelaaja();
        //pelaaja1.taytaTiedot(1);
        Pelaaja pelaaja2 = new Pelaaja();
        //pelaaja2.taytaTiedot(1);
        Pelaaja pelaaja3 = new Pelaaja();
        //pelaaja3.taytaTiedot(1);
    
        pelaajat.lisaa(pelaaja1);
        pelaajat.lisaa(pelaaja2);
        pelaajat.lisaa(pelaaja3);
    
        System.out.println("============= Pelaajat testi =================");
     
        List<Pelaaja> pelaajat2 = pelaajat.annaPelaajat(1);
     
        for (Pelaaja pelaaja : pelaajat2) {
            System.out.print(pelaaja.getJoukkueNro());
            pelaaja.tulosta(System.out);
        }
     
    }
}