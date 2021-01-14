package joukkuerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.*;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author eelia
 * @version 4.8.2020
 *
 */
public class Pelaaja implements Cloneable {
    private int tunnusNro;
    private int joukkueNro;
    private String nimi = "";
    private String syntymaaika = "";
    private String kansalaisuus = "";
    private String pelinumero;
    private String pelipaikka = "";
    private double pituus;
    private double paino;
    
    private static int seuraavaNro;
    
    
    /**
     * Pelaajan alustus
     */
    public Pelaaja() {
        //
    }
    
    
    /**
     * Alustetaan tietyn joukkueen pelaaja
     * @param joukkueNro joukkueen viitenumero
     */
    public Pelaaja(int joukkueNro) {
        this.joukkueNro = joukkueNro;
    }
    

    /**
     * Tulostaa pelaajan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(tunnusNro);
        out.println("Nimi: " + nimi);
        out.println("Syntym‰aika: " + syntymaaika);
        out.println("Kansalaisuus: " + kansalaisuus);
        out.println("Joukkueen id: " + joukkueNro);
        out.println("Pelinumero: " + pelinumero);
        out.println("Pituus: " + pituus);
        out.println("Paino: " + paino);
    }
      
      
    /**
     * tulostaa joukkueen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
      
      
    /**
     * Antaa pelaajalle seuravaan tunnusnumeron
     * @return pelaajan uusi tunnusnumero
     * @example
     * <pre name="test">
     *   Pelaaja p1 = new Pelaaja();
     *   p1.getTunnusNro() === 0;
     *   p1.rekisteroi();
     *   Pelaaja p2 = new Pelaaja();
     *   p2.rekisteroi();
     *   int n1 = p1.getTunnusNro();
     *   int n2 = p2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * Palauttaa pelaajan nimen
     * @return pelaajan nimi
     */
    public String getNimi() {
        return nimi;
    }
    
      
    /**
     * Palauttaa pelaajan tunnusnumeron
     * @return pelaajan id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
     
     
    /**
     * Palauttaa joukkueen tunnusnumeron johon j‰sen kuuluu
     * @return joukkueen id
     */
    public int getJoukkueNro() {
        return joukkueNro;
    }
    
    
    /**
     * Palauttaa pelaajan syntym‰ajan
     * @return pelaajan syntym‰aika
     */
    public String getSyntyma() {
        return syntymaaika;
    }
    
    
    /**
     * Palauttaa pelaajan kansalaisuuden
     * @return pelaajan kansalaisuus
     */
    public String getKansalaisuus() {
        return kansalaisuus;
    }
    
    
    /**
     * Palauttaa pelaajan pelinumeron
     * @return pelaajan pelinumero
     */
    public String getPelinumero() {
        return pelinumero;
    }
    
    
    /**
     * Palauttaa pelaajan pelipaikan
     * @return pelaajan pelipaikka
     */
    public String getPelipaikka() {
        return pelipaikka;
    }
    
    
    /**
     * Palauttaa pelaajan pituuden
     * @return pelaajan pituus
     */
    public double getPituus() {
        return pituus;
    }
    
    
    /**
     * Palauttaa pelaajan painon
     * @return pelaajan paino
     */
    public double getPaino() {
        return paino;
    }
    
    
    /**
     * Laskee pelaajan i‰n syntym‰ajan perusteella
     * @return pelaajan ik‰ vuosina
     * @example
     * <pre name="test">
     *   Pelaaja p1 = new Pelaaja();
     *   p1.setSyntyma("01:01:2000");
     *   p1.getSyntyma() === "01:01:2000";
     *   p1.getIka() === 20;
     * </pre>
     */
    public int getIka() {
        String[] array = syntymaaika.split(":",3);
        LocalDate syntyma = LocalDate.of(Integer.parseInt(array[2]), Integer.parseInt(array[1]), Integer.parseInt(array[0]));
        if (syntymaaika != null) {
            return Period.between(syntyma, LocalDate.now()).getYears();
        }
        return 0;
    }
    
    
    /**
     * @param s pelaajalle asetettava nimi
     * @return virheilmoitus, null jos ok
     */
    public String setNimi(String s) {
        nimi = s;
        return null;
    }
    
    
    /**
     * @param s pelaajalle asetettava syntym‰aika
     * @return virheilmoitus, null jos ok
     */
    public String setSyntyma(String s) {
        syntymaaika = s;
        return null;
    }

    
    /**
     * @param s pelaajalle asetettava kansalaisuus
     * @return virheilmoitus, null jos ok
     */
    public String setKansalaisuus(String s) {
        kansalaisuus = s;
        return s;
    }
    
    
    /**
     * @param s pelaajalle asetettava pelinumero
     * @return virheilmoitus, null jos ok
     */
    public String setPelinumero(String s) {
        pelinumero = s;
        return s;
    }
    
    
    /**
     * @param s pelaajalle asetettava pelipaikka
     * @return virheilmoitus, null jos ok
     */
    public String setPelipaikka(String s) {
        pelipaikka = s;
        return s;
    }
    
    
    /**
     * @param s pelaajalle asetettava pituus
     * @return virheilmoitus, null jos ok
     */
    public String setPituus(String s) {
        pituus = Double.valueOf(s);
        return s;
    }
    
    
    /**
     * @param s pelaajalle asetettava paino
     * @return virheilmoitus, null jos ok
     */
    public String setPaino(String s) {
        paino = Double.valueOf(s);
        return s;
    }
    
    
    /**
     * @param s pelaajalle asetettava joukkueen viite
     * @return virheilmoitus, null jos ok
     */
    public int setJoukkueenro(int s) {
        joukkueNro = s;
        return s;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa ett‰
     * seuraava numero on aina suurempi kuin t‰h‰n menness‰ suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * Palauttaa pelaajan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return pelaaja tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     *   Pelaaja pelaaja = new Pelaaja();
     *   pelaaja.parse("   1   |  2  |   Erkki Esimerkki  | 01:01:2000 | suomalainen | 23 | Puolustaja | 180.0 | 80.0 ");
     *   pelaaja.toString()    === "1|2|Erkki Esimerkki|01:01:2000|suomalainen|23|Puolustaja|180.0|80.0";
     * </pre> 
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + joukkueNro + "|" + nimi + "|" + syntymaaika + "|" + kansalaisuus + "|" + pelinumero + "|" + pelipaikka + "|" + pituus + "|" + paino;
    }

    
    /**
     * Selvit‰‰ pelaajan tiedot | erotellusta merkkijonosta.
     * Pit‰‰ huolen ett‰ seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta pelaajan tiedot otetaan
     *  @example
     * <pre name="test">
     *   Pelaaja pelaaja = new Pelaaja();
     *   pelaaja.parse("   1   |  2  |   Erkki Esimerkki  | 01:01:2000 | suomalainen | 23 | Puolustaja | 180.0 | 80.0 ");
     *   pelaaja.toString()  === "1|2|Erkki Esimerkki|01:01:2000|suomalainen|23|Puolustaja|180.0|80.0";
     *   
     *   pelaaja.rekisteroi();
     *   int n = pelaaja.getTunnusNro();
     *   pelaaja.parse(""+(n+20));
     *   pelaaja.rekisteroi();
     *   pelaaja.getTunnusNro() === n+20+1;
     *   pelaaja.toString()     === "" + (n+20+1) + "|2|Erkki Esimerkki|01:01:2000|suomalainen|23|Puolustaja|180.0|80.0";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        joukkueNro = Mjonot.erota(sb, '|', joukkueNro);
        nimi = Mjonot.erota(sb, '|', nimi);
        syntymaaika = Mjonot.erota(sb, '|', syntymaaika);
        kansalaisuus = Mjonot.erota(sb, '|', kansalaisuus);
        pelinumero = Mjonot.erota(sb, '|', pelinumero);
        pelipaikka = Mjonot.erota(sb, '|', pelipaikka);
        pituus = Mjonot.erota(sb, '|', pituus);
        paino = Mjonot.erota(sb, '|', paino);
    }
     
     
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
         
     
    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    
    /**
     * Tehd‰‰n identtinen klooni pelaajasta
     * @return Object kloonattu pelaaja
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Pelaaja pelaaja = new Pelaaja();
     *   pelaaja.parse("   1   |  2  |   Erkki Esimerkki  | 01:01:2000 | suomalainen | 23 | Puolustaja | 180.0 | 80.0 ");
     *   Pelaaja kopio = pelaaja.clone();
     *   kopio.toString() === pelaaja.toString();
     *   pelaaja.parse("   2   |  2  |   Pertti Esimerkki  | 01:01:2000 | suomalainen | 23 | Puolustaja | 180.0 | 80.0 ");
     *   kopio.toString().equals(pelaaja.toString()) === false;
     * </pre>
     */
    @Override
    public Pelaaja clone() throws CloneNotSupportedException {
        Pelaaja uusi;
        uusi = (Pelaaja) super.clone();
        return uusi;
    }
    
    
    /**
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Pelaaja pelaaja = new Pelaaja();
        pelaaja.rekisteroi();
        pelaaja.syntymaaika = "01:01:2020";
        //pelaaja.taytaTiedot(1);
        System.out.println(pelaaja.getIka());
    }
}