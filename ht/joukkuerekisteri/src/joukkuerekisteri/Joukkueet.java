package joukkuerekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Sarjan joukkueita hallinnoiva luokka, osaa esim lisätä uuden joukkueen
 * @author eelia
 * @version 5.8.2020
 */
public class Joukkueet implements Iterable<Joukkue>{
    private static final int MAX_joukkueita = 8;
    private int lkm;
    private boolean muutettu = false;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "";
    private Joukkue[] alkiot = new Joukkue[MAX_joukkueita];
    
    
    
    
    
    /**
     * Lisää uuden joukkueen tietorakenteeseen
     * @param joukkue viite lisättävään joukkueeseen
     * @throws SailoException jos ei ole tilaa uudelle joukkueelle
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.util.*;
     * 
     * Joukkueet joukkueet = new Joukkueet();
     * Joukkue joukkue1 = new Joukkue(), joukkue2 = new Joukkue();
     * joukkueet.getLkm() === 0;
     * joukkueet.lisaa(joukkue1); joukkueet.getLkm() === 1;
     * joukkueet.lisaa(joukkue2); joukkueet.getLkm() === 2;
     * joukkueet.lisaa(joukkue1); joukkueet.getLkm() === 3;
     * Iterator<Joukkue> it = joukkueet.iterator(); 
     * it.next() === joukkue1;
     * it.next() === joukkue2; 
     * it.next() === joukkue1;  
     * joukkueet.lisaa(joukkue1); joukkueet.getLkm() === 4;
     * joukkueet.lisaa(joukkue1); joukkueet.getLkm() === 5;
     * joukkueet.lisaa(joukkue1); joukkueet.getLkm() === 6;
     * joukkueet.lisaa(joukkue1); joukkueet.getLkm() === 7;
     * joukkueet.lisaa(joukkue1); joukkueet.getLkm() === 8;
     * joukkueet.lisaa(joukkue1); joukkueet.getLkm() === 9;
     * </pre>
     */
    public void lisaa(Joukkue joukkue) throws SailoException {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+10); 
        alkiot[lkm] = joukkue;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * Korvaa alkuperäisen joukkueen muokatulla joukkueella tai lisää sen tietorakenteeseen
     * @param joukkue korvattava tai lisättävä joukkue
     * @throws SailoException jos ei tilaa
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Joukkueet joukkueet = new Joukkueet();
     * Joukkue joukkue1 = new Joukkue(), joukkue2 = new Joukkue();
     * joukkue1.rekisteroi(); joukkue2.rekisteroi();
     * joukkueet.getLkm() === 0;
     * joukkueet.korvaaTaiLisaa(joukkue1); joukkueet.getLkm() === 1;
     * joukkueet.korvaaTaiLisaa(joukkue2); joukkueet.getLkm() === 2;
     * Joukkue joukkue3 = joukkue1.clone();
     * joukkueet.korvaaTaiLisaa(joukkue3); joukkueet.getLkm() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Joukkue joukkue) throws SailoException {
            int id = joukkue.getTunnusNro();
            for (int i = 0; i < lkm; i++) {
                if ( alkiot[i].getTunnusNro() == id ) {
                    alkiot[i] = joukkue;
                    muutettu = true;
                    return;
                }
            }
            lisaa(joukkue);
    }
    
    
    /**
     * Lukee joukkueet tiedostosta
     * @param tied tiedoston perusnimi
     * @throws SailoException jos epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Joukkueet joukkueet = new Joukkueet();
     *  Joukkue j1 = new Joukkue(), j2 = new Joukkue();
     *  String hakemisto = "testisarja";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  joukkueet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  joukkueet.lisaa(j1);
     *  joukkueet.lisaa(j2);
     *  joukkueet.tallenna();
     *  joukkueet = new Joukkueet();
     *  joukkueet.lueTiedostosta(tiedNimi);
     *  Iterator<Joukkue> i = joukkueet.iterator();
     *  i.next() === j1;
     *  i.next() === j2;
     *  i.hasNext() === false;
     *  joukkueet.lisaa(j2);
     *  joukkueet.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Sarjan nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Joukkue joukkue = new Joukkue();
                joukkue.parse(rivi); // voisi olla virhekäsittely
                lisaa(joukkue);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**
     * Tallentaa joukkueet tiedostoon
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (Joukkue joukkue : this) {
                fo.println(joukkue.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }    
        muutettu = false;
    }
    
    
    /**
     * Poistaa joukkueen, jolla valittu tunnusnumero
     * @param id poistettavan joukkueen tunnusnumero
     * @return palauttaa 1 jos poistettiin, muuten 0
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Joukkueet joukkueet = new Joukkueet(); 
     * Joukkue joukkue1 = new Joukkue(), joukkue2 = new Joukkue(), joukkue3 = new Joukkue(); 
     * joukkue1.rekisteroi(); joukkue2.rekisteroi(); joukkue3.rekisteroi(); 
     * int id1 = joukkue1.getTunnusNro(); 
     * joukkueet.lisaa(joukkue1); joukkueet.lisaa(joukkue2); joukkueet.lisaa(joukkue3); 
     * joukkueet.poista(id1+1) === 1; 
     * joukkueet.anna(id1+1) === null; joukkueet.getLkm() === 2; 
     * joukkueet.poista(id1) === 1; joukkueet.getLkm() === 1; 
     * joukkueet.poista(id1+3) === 0; joukkueet.getLkm() === 1; 
     * </pre>
     */
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    } 
    
    
    /**
     * Etsii joukkueen id:n perusteella
     * @param id etsittävän joukkueen id
     * @return löytyneen joukkueen indeksi tai -1 jos ei löydy 
     * @example
     * <pre name="test"> 
     * #THROWS SailoException  
     * Joukkueet joukkueet = new Joukkueet(); 
     * Joukkue j1 = new Joukkue(), j2 = new Joukkue(), j3 = new Joukkue(); 
     * j1.rekisteroi(); j2.rekisteroi(); j3.rekisteroi(); 
     * int id1 = j1.getTunnusNro(); 
     * joukkueet.lisaa(j1); joukkueet.lisaa(j2); joukkueet.lisaa(j3); 
     * joukkueet.etsiId(id1+1) === 1; 
     * joukkueet.etsiId(id1+2) === 2; 
     * </pre> 
     */
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    }
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien joukkueiden viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenne löytyneistä joukkueista 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Joukkueet joukkueet = new Joukkueet(); 
     *   Joukkue j1 = new Joukkue(); j1.parse("1|FC Honkola|Äänekoski|2010|"); 
     *   Joukkue j2 = new Joukkue(); j2.parse("2|AC Hietama|Äänekoski|2020|"); 
     *   Joukkue j3 = new Joukkue(); j3.parse("3|AC Konginkangas|Äänekoski|2020|"); 
     *   Joukkue j4 = new Joukkue(); j4.parse("4|FC Kortepohja|Jyväskylä|2020|"); 
     *   Joukkue j5 = new Joukkue(); j5.parse("5|FC Keltinmäki|Jyväskylä|2010|"); 
     *   joukkueet.lisaa(j1); joukkueet.lisaa(j2); joukkueet.lisaa(j3); joukkueet.lisaa(j4); joukkueet.lisaa(j5);
     *   List<Joukkue> loytyneet;  
     *   loytyneet = (List<Joukkue>)joukkueet.etsi("*FC*",0);  
     *   loytyneet.size() === 3;  
     *   loytyneet.get(0) == j1 === true;  
     *   loytyneet.get(2) == j4 === true;
     *   loytyneet.get(1) == j5 === true; 
     *     
     *   loytyneet = (List<Joukkue>)joukkueet.etsi("*2020*",2);  
     *   loytyneet.size() === 3;  
     *   loytyneet.get(0) == j2 === true;  
     *   loytyneet.get(1) == j3 === true;
     *   loytyneet.get(2) == j4 === true;
     *   
     *   loytyneet = (List<Joukkue>)joukkueet.etsi("*Jyväskylä*",1);  
     *   loytyneet.size() === 2;  
     *   loytyneet.get(0) == j4 === true;  
     *   loytyneet.get(1) == j5 === true;
     *     
     *   loytyneet = (List<Joukkue>)joukkueet.etsi(null,-1);  
     *   loytyneet.size() === 5;  
     * </pre> 
     */
    public Collection<Joukkue> etsi(String hakuehto, int k) { 
        String ehto = "*"; 
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
        int hk = k; 
        if ( hk < 0 ) hk = 0; // jotta etsii id:n mukaan 
        List<Joukkue> loytyneet = new ArrayList<Joukkue>(); 
        for (Joukkue joukkue : this) { 
            if (WildChars.onkoSamat(joukkue.anna(hk), ehto)) loytyneet.add(joukkue);   
        } 
        Collections.sort(loytyneet, new Joukkue.Vertailija(hk)); 
        return loytyneet; 
    }
    
    
    /**
     * Paluttaa sarjan koko nimen
     * @return Sarjan nimi 
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    
    /**
     * Palauttaa tallennukseen käytettävän tiedoston nimen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    
    
    /**
     * Palauttaa tallennukseen käytettävän tiedoston nimen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    
    /**
     * Palauttaa sarjan joukkueiden lukumäärän
     * @return joukkueiden lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Palauttaa viitteen i:teen joukkueeseen
     * @param i monennenko joukkueen viite halutaan
     * @return viite joukkueeseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Joukkue anna(int i) throws IndexOutOfBoundsException {
        if(i < 0 || lkm <= i)
            return null;
        return alkiot[i];
    }
    
    
    /**
     * Luokka joukkueiden iteroimiseksi
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Joukkueet joukkueet = new Joukkueet();
     * Joukkue j1 = new Joukkue(), j2 = new Joukkue();
     * j1.rekisteroi(); j2.rekisteroi();
     *
     * joukkueet.lisaa(j1); 
     * joukkueet.lisaa(j2); 
     * joukkueet.lisaa(j1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Joukkue joukkue:joukkueet)
     *   ids.append(" "+joukkue.getTunnusNro());           
     * 
     * String tulos = " " + j1.getTunnusNro() + " " + j2.getTunnusNro() + " " + j1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Joukkue>  i=joukkueet.iterator(); i.hasNext(); ) {
     *   Joukkue joukkue = i.next();
     *   ids.append(" "+joukkue.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Joukkue>  i=joukkueet.iterator();
     * i.next() == j1  === true;
     * i.next() == j2  === true;
     * i.next() == j1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class JoukkueetIterator implements Iterator<Joukkue> {
        private int kohdalla = 0;
            
    
        /**
         * Onko olemassa vielä seuraavaa joukkuetta
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä joukkueita
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        
        
        /**
         * Annetaan seuraava joukkue
         * @return seuraava joukkue
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Joukkue next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }
        
        
        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
        
        
    /**
     * Palautetaan iteraattori joukkueistaan.
     * @return joukkue iteraattori
     */
    @Override
    public Iterator<Joukkue> iterator() {
        return new JoukkueetIterator();
    }
    
    
    /**
     * vastaa joukkueiden tiedoista
     * osaa lisätä ja poistaa joukkueita
     * lukee ja kirjoittaa joukkueet-tiedostoon
     * osaa etsiä ja lajitella
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Joukkueet joukkueet = new Joukkueet();
        
        Joukkue tiimi = new Joukkue(); 
        Joukkue tiimi2 = new Joukkue();
        tiimi.rekisteroi();
        tiimi2.rekisteroi();
        //tiimi.taytaTiedot();
        //tiimi2.taytaTiedot();
        
        try {
            joukkueet.lisaa(tiimi);
            joukkueet.lisaa(tiimi2);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        System.out.println("========== Joukkueet testi ==============");
        for (int i=0; i<joukkueet.getLkm(); i++) {
                Joukkue joukkue = joukkueet.anna(i);
                System.out.println("Joukkue paikassa: " + i);
                joukkue.tulosta(System.out);
        }
    }
}
