package fxJoukkuerekisteri;

import java.net.URL;
import java.util.*;
import fi.jyu.mit.fxgui.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.*;
import joukkuerekisteri.Pelaaja;
import tarkistus.syntymaAikaTarkistus;

/**
 * Kysyt‰‰n pelaajan tiedot luomalla uusi dialogi
 * @author eelia
 * @version 30.7.2020
 *
 */
public class MuokkaaPelaajaController implements ModalControllerInterface<Pelaaja>,Initializable{
    @FXML private TextField textNimi;
    @FXML private TextField textVuosi;
    @FXML private TextField textSyntyma;
    @FXML private TextField textKansalaisuus;
    @FXML private TextField textPelinumero;
    @FXML private TextField textPelipaikka;
    @FXML private TextField textPituus;
    @FXML private TextField textPaino;
    @FXML private Label labelVirhe;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
        
    
    @FXML private void handleOK() {
        tarkistaTiedot();
    }
    
        
    @FXML private void handleCancel() {
        pelaajaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    
// ========================================================    
    private Pelaaja pelaajaKohdalla;
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
     * Tarkistaa annettujen tietojen oikeellisuuden
     */
    private void tarkistaTiedot() {
        if ( pelaajaKohdalla != null && pelaajaKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhj‰");
            return;
        }
        if ( pelaajaKohdalla != null && pelaajaKohdalla.getSyntyma().trim().equals("") ) {
            naytaVirhe("Syntym‰aika ei saa olla tyhj‰");
            return;
        }
        if ( syntymaAikaTarkistus.tarkista(pelaajaKohdalla.getSyntyma().trim()) == false ) {
            naytaVirhe("Syntym‰ajan tulee olla muodossa \"dd:MM:yyyy\" ja sen tulee olla olemassa oleva p‰iv‰m‰‰r‰");
            return;
        }
        if ( pelaajaKohdalla != null && pelaajaKohdalla.getKansalaisuus().trim().equals("") ) {
            naytaVirhe("Kansalaisuus ei saa olla tyhj‰");
            return;
        }
        try {
            String s = pelaajaKohdalla.getPelinumero().trim();
            Integer.parseInt(s);
        } catch (Exception e) {
            naytaVirhe("Pelinumeron t‰ytyy olla kokonaisluku v‰lilt‰ 1-99");
            return;
        }
        if ( Integer.valueOf(pelaajaKohdalla.getPelinumero().trim()) > 99 || Integer.valueOf(pelaajaKohdalla.getPelinumero().trim()) < 1) {
            naytaVirhe("Pelinumeron t‰ytyy olla v‰lilt‰ 1-99");
            return;
        }   
        String[] pelipaikat = {"Hyˆkk‰‰j‰","Keskikentt‰","Puolustaja","Maalivahti"};
        int n = 0;
        for ( String p : pelipaikat ) {
            if ( pelaajaKohdalla.getPelipaikka().trim().equals(p) ) n++;
        }
        if ( n == 0 ) {
            naytaVirhe("Pelipaikan tulee olla \"Hyˆkk‰‰j‰\", \"Keskikentt‰\", \"Puolustaja\" tai \"Maalivahti\"");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
        
        
    /**
     * Tekee tarvittavat muut alustukset. Mm laittaa edit-kentist‰ tulevan
     * tapahtuman menem‰‰n kasitteleMuutosPelaajaan-metodiin ja vie sille
     * kent‰nnumeron parametrina.
     */
    protected void alusta() {
        edits = new TextField[]{textNimi, textSyntyma, textKansalaisuus, textPelinumero, textPelipaikka, textPituus, textPaino};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosPelaajaan(k, (TextField)(e.getSource())));
        }
    }
        
        
    @Override
    public void setDefault(Pelaaja oletus) {
        pelaajaKohdalla = oletus;
        naytaPelaaja(edits, pelaajaKohdalla);
    }
    
        
    @Override
    public Pelaaja getResult() {
        return pelaajaKohdalla;
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
     * K‰sitell‰‰n pelaajaan tullut muutos
     * @param edit muuttunut kentt‰
     */
    private void kasitteleMuutosPelaajaan(int k, TextField edit) {
        if (pelaajaKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1 : virhe = pelaajaKohdalla.setNimi(s); break;
            case 2 : virhe = pelaajaKohdalla.setSyntyma(s); break;
            case 3 : virhe = pelaajaKohdalla.setKansalaisuus(s); break;
            case 4 : virhe = pelaajaKohdalla.setPelinumero(s); break;
            case 5 : virhe = pelaajaKohdalla.setPelipaikka(s); break;
            case 6 : virhe = pelaajaKohdalla.setPituus(s); break;
            case 7 : virhe = pelaajaKohdalla.setPaino(s); break;
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
     * N‰ytet‰‰n pelaajan tiedot TextField komponentteihin
     * @param edits taulukko jossa tekstikentti‰
     * @param pelaaja n‰ytett‰v‰ pelaaja
     */
    public static void naytaPelaaja(TextField[] edits, Pelaaja pelaaja) {
        if (pelaaja == null) return;
        edits[0].setText(pelaaja.getNimi());
        edits[1].setText(pelaaja.getSyntyma());
        edits[2].setText(pelaaja.getKansalaisuus());
        edits[3].setText(pelaaja.getPelinumero());
        edits[4].setText(pelaaja.getPelipaikka());
        edits[5].setText(Double.toString(pelaaja.getPituus()));
        edits[6].setText(Double.toString(pelaaja.getPaino()));
    }
    
    
    /**
     * Luodaan pelaajan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit‰ dataan n‰ytet‰‰n oletuksena
     * @return null jos painetaan Cancel, muuten t‰ytetty tietue
     */
    public static Pelaaja kysyPelaaja(Stage modalityStage, Pelaaja oletus) {
        return ModalController.<Pelaaja, MuokkaaPelaajaController>showModal(
                MuokkaaPelaajaController.class.getResource("MuokkaaPelaajaView.fxml"), "Muokkaa pelaajaa", modalityStage, oletus, null);
    }
}
