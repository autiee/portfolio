package fxJoukkuerekisteri;

import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import joukkuerekisteri.Joukkuerekisteri;

/**
 * @author eelia
 * @version 5.8.2020
 *
 */
public class TietoaSarjastaController implements ModalControllerInterface<Joukkuerekisteri> {
        
    @FXML private TextField textJMaara;
    @FXML private TextField textPMaara;
    @FXML private TextField textHyokkaajat;
    @FXML private TextField textKeskiK;
    @FXML private TextField textPuolustajat;
    @FXML private TextField textMaalivahdit;
    @FXML private TextField textPPituus;
    @FXML private TextField textPPaino;
    @FXML private TextField textPIka;
    
//===========================================================================================    
//Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia    
   
    private Joukkuerekisteri joukkuerekisteri;
    
    
    /**
     * Alustetaan sarjan tiedot 
     */
    public void alusta() {
        textJMaara.setText(String.valueOf(joukkuerekisteri.getJoukkueita()));
        textPMaara.setText(String.valueOf(joukkuerekisteri.getPelaajia()));
        textHyokkaajat.setText(joukkuerekisteri.getPelipaikkaMaara("Hyökkääjä"));
        textKeskiK.setText(joukkuerekisteri.getPelipaikkaMaara("Keskikenttä"));
        textPuolustajat.setText(joukkuerekisteri.getPelipaikkaMaara("Puolustaja"));
        textMaalivahdit.setText(joukkuerekisteri.getPelipaikkaMaara("Maalivahti"));
        textPPituus.setText(joukkuerekisteri.getPituus());
        textPPaino.setText(joukkuerekisteri.getPaino());
        textPIka.setText(joukkuerekisteri.getIka());
    }


    @Override
    public Joukkuerekisteri getResult() {
        return joukkuerekisteri;
    }


    @Override
    public void handleShown() {
        alusta();
    }


    @Override
    public void setDefault(Joukkuerekisteri oletus) {
        joukkuerekisteri = oletus;
    }
}