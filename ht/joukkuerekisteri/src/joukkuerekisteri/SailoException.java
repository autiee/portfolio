package joukkuerekisteri;


/**
 * @author Eeli Autio
 * @version 5.8.2020
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
      
    
    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * k�ytett�v� viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}

