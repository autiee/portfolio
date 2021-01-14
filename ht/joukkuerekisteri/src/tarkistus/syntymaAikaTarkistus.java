package tarkistus;

import java.util.Calendar;

/**
 * Luokka pelaajalle annetun syntymäajan tarkistamiseksi
 * @author eelia
 * @version 5.8.2020
 *
 */
public class syntymaAikaTarkistus {
    /** Taulukko kuukausien pituuksista. Oma rivi  karkausvuosille */
    public static final int KPITUUDET[][] = {
            // 1  2  3  4  5  6  7  8  9 10 11 12
            { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
            { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }
    };


    /**
     * Palautetaan tieto siitä onko tutkittava vuosi karkausvuosi vai ei
     * @param vv tutkittava vuosi
     * @return 1 jos on karkausvuosi ja 0 jos ei ole
     * @example
     * <pre name="test">
     *   karkausvuosi(1900) === 0
     *   karkausvuosi(1900) === 0
     *   karkausvuosi(1901) === 0
     *   karkausvuosi(1996) === 1
     *   karkausvuosi(2000) === 1
     *   karkausvuosi(2001) === 0
     *   karkausvuosi(2004) === 1
     * </pre>
     */
    public static int karkausvuosi(int vv) {
        if ( vv % 400 == 0 ) return 1;
        if ( vv % 100 == 0 ) return 0;
        if ( vv % 4 == 0 ) return 1;
        return 0;
    }



    /**
     * Tarkistaa, onko syntymäaika sallittu
     * @param syntymaAika Käytettävä syntymäaika
     * @return palauttaa false jos syntymäaika ei ole sallittu, muuten true
     * @example
     * <pre name="test">
     *   String s = "01:01:2000";
     *   tarkista(s) === true;
     *   s = "60:01:2000";
     *   tarkista(s) === false;
     *   s = "01:13:2000";
     *   tarkista(s) === false;
     *   s = "01:01:2030";
     *   tarkista(s) === false;
     * </pre>
     */
    public static boolean tarkista(String syntymaAika) {
        int pv;
        int kk;
        int vv;
        String[] syntyma = syntymaAika.split(":",3);
        try {
            pv = Integer.parseInt(syntyma[0]);
            kk = Integer.parseInt(syntyma[1]);
            vv = Integer.parseInt(syntyma[2]);
        } catch(Exception e) {
            return false;
        }
        if(vv < 0 || vv > Calendar.getInstance().get(Calendar.YEAR)) return false;
        if(pv > 0){
            int pv_lkm;
            int kv = karkausvuosi(vv); //palauttaa 0 jos ei, 1 jos on
            if (kk > 0 && kk <= 12){
                pv_lkm = KPITUUDET[kv][kk - 1]; 
            }
            else return false;
            if (pv_lkm < pv) {
                return false;
            }
        }
        return true;
    }
}