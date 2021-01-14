package joukkuerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * tietää joukkueen kentät(nimi, perustusvuosi jne.)                   
 * osaa tarkistaa onko tietty kenttä syntaktisesti                      
 * oikein                                                               
 * osaa muuttaa merkkijonon joukkueen tiedoiksi                         
 * osaa antaa merkkijonona tietyn kentän tiedot                         
 * osaa laittaa tietyn merkkijonon tiettyyn kenttään
 * @author eelia
 * @version 4.8.2020
 *
 */
public class Joukkue implements Cloneable{
    private int tunnusNro;
    private String nimi = "";
    private String kotipaikkakunta = "";
    private String perustamisvuosi;
    private String kotikentta = "";
    
    private static int seuraavaNro = 1;
    
    
    /** 
     * Joukkueiden vertailija 
     */ 
    public static class Vertailija implements Comparator<Joukkue> { 
        private int k;  
             
        @SuppressWarnings("javadoc") 
        public Vertailija(int k) { 
            this.k = k; 
        } 
             
        @Override 
        public int compare(Joukkue j1, Joukkue j2) { 
            return j1.anna(k).compareToIgnoreCase(j2.anna(k)); 
        } 
    } 
    
    
    /**
     * Palauttaa hakemisen kannalta oleellisten kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 3;
    }
    
    
    /**
     * Palauttaa k:tta joukkueen kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "nimi";
        case 1: return "kotipaikkakunta";
        case 2: return "perustusvuosi";
        default: return "Oletus";
        }
    }
    
    
    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return ekan kentän indeksi
     */
    public int ekaKentta() {
        return 0;
    }
    
    
    /**
     * Antaa joukkueelle tunnusnumeron
     * @return joukkueen uusi tunnusnumero
     * @example
     * <pre name="test">
     *   Joukkue joukkue1 = new Joukkue();
     *   joukkue1.getTunnusNro() === 0;
     *   joukkue1.rekisteroi();
     *   Joukkue joukkue2 = new Joukkue();
     *   joukkue2.rekisteroi();
     *   int n1 = joukkue1.getTunnusNro();
     *   int n2 = joukkue2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        if(tunnusNro != 0) return tunnusNro;
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * Palauttaa joukkueen tunnusnumeron
     * @return joukkueen tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * @return joukkueen perustamisvuosi
     */
    public String getVuosi() {
        return perustamisvuosi;
    }
    
    
    /**
     * @return joukkueen kotikenttä
     */
    public String getKotikentta() {
        return kotikentta;
    }
    
    
    /**
     * @return joukkueen kotipaikkakunta
     */
    public String getKotipaikkakunta() {
        return kotipaikkakunta;
    }
    
    
    /**
     * Palauttaa joukkueen nimen
     * @return joukkueen nimi
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * @param s joukkueelle asetettava nimi
     * @return virheilmoitus, null jos ok
     */
    public String setNimi(String s) {
           nimi = s;
           return null;
    }
    
    
    /**
     * @param s joukkueelle asetettava kotipaikkakunta
     * @return virheilmoitus, null jos ok
     */
    public String setKunta(String s) {
           kotipaikkakunta = s;
           return null;
    }
      
          
    /**
     * @param s joukkueelle asetettava perustamisvuosi
     * @return virheilmoitus, null jos ok
     */
    public String setVuosi(String s) {
           perustamisvuosi = s;
           return null;
    }
     
         
    /**
     * @param s joukkueelle asetettava kotikenttä
     * @return virheilmoitus, null jos ok
     */
    public String setKentta(String s) {
        kotikentta= s;
        return null;
    }
    
    
    /**
     * Tulostaa joukkueen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(tunnusNro);
        out.println("Nimi: " + nimi);
        out.println("Kotipaikkakunta: " + kotipaikkakunta);
        out.println("Perustamisvuosi: " + perustamisvuosi);
        out.println("Kotikenttä: " + kotikentta);
    }
    
    
    /**
     * Tulostaa joukkueen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }    
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * Palauttaa joukkueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return joukkue tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     *   Joukkue joukkue = new Joukkue();
     *   joukkue.parse("   1  |  FC Honkola   | Äänekoski");
     *   joukkue.toString().startsWith("1|FC Honkola|Äänekoski|") === true;
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + nimi + "|" + kotipaikkakunta + "|" + perustamisvuosi + "|" + kotikentta;
    }
     
          
    /**
     * Selvitää joukkueen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta joukkueen tiedot otetaan
     * @example
     * <pre name="test">
     *   Joukkue joukkue = new Joukkue();
     *   joukkue.parse("   3  |  FC Honkola   | Äänekoski");
     *   joukkue.getTunnusNro() === 3;
     *   joukkue.toString().startsWith("3|FC Honkola|Äänekoski|") === true;
     *   
     *   joukkue.rekisteroi();
     *   int n = joukkue.getTunnusNro();
     *   joukkue.parse(""+(n+20));
     *   joukkue.rekisteroi();    
     *   joukkue.getTunnusNro() === n+20;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        kotipaikkakunta = Mjonot.erota(sb, '|', kotipaikkakunta);
        perustamisvuosi = Mjonot.erota(sb, '|', perustamisvuosi);
        kotikentta = Mjonot.erota(sb, '|', kotikentta);
    }
         
         
    @Override
    public boolean equals(Object joukkue) {
        if ( joukkue == null ) return false;
        return this.toString().equals(joukkue.toString());
    }
     
     
    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    
    /**
     * Tehdään identtinen klooni joukkueesta
     * @return Object kloonattu joukkue
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Joukkue joukkue = new Joukkue();
     *   joukkue.parse("   3  |  FC Honkola   | Äänekoski");
     *   Joukkue kopio = joukkue.clone();
     *   kopio.toString() === joukkue.toString();
     *   joukkue.parse("   4  |  FC Konginkangas   | Äänekoski");
     *   kopio.toString().equals(joukkue.toString()) === false;
     * </pre>
     */
    @Override
    public Joukkue clone() throws CloneNotSupportedException {
             Joukkue uusi;
             uusi = (Joukkue) super.clone();
             return uusi;
    }
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + nimi;
        case 1: return "" + kotipaikkakunta;
        case 2: return "" + perustamisvuosi;
        default: return "Oletus";
        }
    }
    
    
    /**
     * Testataan luokkaa
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Joukkue tiimi = new Joukkue(); 
        Joukkue tiimi2 = new Joukkue();
        tiimi.rekisteroi();
        tiimi2.rekisteroi();
        tiimi.tulosta(System.out);
        //tiimi.taytaTiedot();
        tiimi.tulosta(System.out);

        tiimi2.tulosta(System.out);
        //tiimi2.taytaTiedot();
        tiimi2.tulosta(System.out);
    }
}


