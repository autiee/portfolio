package fxJoukkuerekisteri;

import java.net.URL;
import java.util.*;
import fi.jyu.mit.fxgui.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.*;
import joukkuerekisteri.Joukkue;

/**
 * Kysyt‰‰n joukkueen tiedot luomalla uusi dialogi
 * @author eelia
 * @version 30.7.2020
 *
 */
public class MuokkaaJoukkueController implements ModalControllerInterface<Joukkue>,Initializable{
    @FXML private TextField textVuosi;
    @FXML private TextField textKunta;
    @FXML private TextField textKentta;
    @FXML private TextField textNimi;
    @FXML private Label labelVirhe;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
        
    @FXML private void handleOK() {
        tarkistaTiedot();
    }
    

    @FXML private void handleCancel() {
        joukkueKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
// ========================================================    
    private Joukkue joukkueKohdalla;
    private TextField edits[];
       
    
    /**
     * Tyhjent‰‰ tekstikent‰t 
     * @param edits taulukko jossa tyhjennett‰vi‰ tekstikentti‰
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }
    
    
    /**
     * Tekee tarvittavat muut alustukset. Mm laittaa edit-kentist‰ tulevan
     * tapahtuman menem‰‰n kasitteleMuutosJoukkueeseen-metodiin ja vie sille
     * kent‰nnumeron parametrina.
     */
    protected void alusta() {
        edits = new TextField[]{textNimi, textKunta, textVuosi, textKentta};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosJoukkueeseen(k, (TextField)(e.getSource())));
        }
    }
        
    /**
     * Tarkistaa dialogissa annettujen tiedon oikeellisuuden    
     */
    private void tarkistaTiedot() {
        if ( joukkueKohdalla != null && joukkueKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhj‰");
            return;
        }
        if ( joukkueKohdalla != null && joukkueKohdalla.getKotipaikkakunta().trim().equals("") ) {
            naytaVirhe("Kotipaikkakunta ei saa olla tyhj‰");
            return;
        }
        try {
            String s = joukkueKohdalla.getVuosi().trim();
            Integer.parseInt(s);
        } catch (Exception e) {
            naytaVirhe("Perustamisvuoden t‰ytyy olla kokonaisluku");
            return;
        }
        if ( Integer.valueOf(joukkueKohdalla.getVuosi().trim()) > Calendar.getInstance().get(Calendar.YEAR) ) {
            naytaVirhe("Perustamisvuosi ei ole sallitulla alueella");
            return;
        }
        if ( joukkueKohdalla != null && joukkueKohdalla.getKotikentta().trim().equals("") ) {
            naytaVirhe("Kotikentt‰ ei saa olla tyhj‰");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
        
        
    @Override
    public void setDefault(Joukkue oletus) {
        joukkueKohdalla = oletus;
        naytaJoukkue(edits, joukkueKohdalla);
    }
    
        
    @Override
    public Joukkue getResult() {
        return joukkueKohdalla;
    }
        
        
    /**
     * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
     */
    @Override
    public void handleShown() {
        textNimi.requestFocus();
    }
        
        
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
        
    /**
     * K‰sitell‰‰n joukkueeseen tullut muutos
     * @param edit muuttunut kentt‰
     */
    private void kasitteleMuutosJoukkueeseen(int k, TextField edit) {
        if (joukkueKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1 : virhe = joukkueKohdalla.setNimi(s); break;
            case 2 : virhe = joukkueKohdalla.setKunta(s); break;
            case 3 : virhe = joukkueKohdalla.setVuosi(s); break;
            case 4 : virhe = joukkueKohdalla.setKentta(s); break;
            default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * N‰ytet‰‰n joukkueen tiedot TextField komponentteihin
     * @param edits taulukko jossa tekstikentti‰
     * @param joukkue n‰ytett‰v‰ joukkue
     */
    public static void naytaJoukkue(TextField[] edits, Joukkue joukkue) {
        if (joukkue == null) return;
        edits[0].setText(joukkue.getNimi());
        edits[1].setText(joukkue.getKotipaikkakunta());
        edits[2].setText(joukkue.getVuosi());
        edits[3].setText(joukkue.getKotikentta());
    }
    
    
    /**
     * Luodaan joukkueen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit‰ dataan n‰ytet‰‰n oletuksena
     * @return null jos painetaan Cancel, muuten t‰ytetty tietue
     */
    public static Joukkue kysyJoukkue(Stage modalityStage, Joukkue oletus) {
        return ModalController.<Joukkue, MuokkaaJoukkueController>showModal(
                MuokkaaJoukkueController.class.getResource("MuokkaaJoukkueView.fxml"), "Muokkaa joukkuetta", modalityStage, oletus, null);
    }
}
