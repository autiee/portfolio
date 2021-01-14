package fxJoukkuerekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import joukkuerekisteri.*;

/**
 * @author eelia
 * @version 5.8.2020
 *
 */
public class JoukkuerekisteriGUIController implements Initializable, ModalControllerInterface<String> {
    
    @FXML private ScrollPane panelJoukkue;
    @FXML private ListChooser<Joukkue> chooserJoukkueet;
    @FXML private ListChooser<Pelaaja> chooserPelaajat;
    @FXML private ComboBoxChooser<String> cbEhdot;
    @FXML private TextField textHakuehto;
    @FXML private TextField textYksi;
    @FXML private TextField textKaksi;
    @FXML private TextField textKolme;
    @FXML private TextField textNelja;
    @FXML private TextField textViisi;
    @FXML private TextField textKuusi;
    @FXML private TextField textSeitseman;
    @FXML private TextField textKahdeksan;
    @FXML private Label labelYksi;
    @FXML private Label labelKaksi;
    @FXML private Label labelKolme;
    @FXML private Label labelNelja;
    @FXML private Label labelViisi;
    @FXML private Label labelKuusi;
    @FXML private Label labelSeitseman;
    @FXML private Label labelKahdeksan;
    @FXML private Label labelVirhe;
    
            
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }
    
    
    @FXML private void handleTallenna() {
        tallenna();
    }
      
      
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
      
      
    @FXML private void handleLisaajoukkue() {
        lisaaJoukkue();
    }
      
      
    @FXML private void handleLisaapelaaja() {
        lisaaPelaaja();
    }
      
      
    @FXML private void handleMuokkaaJoukkue() {
        muokkaa();
    }
      
      
    @FXML private void handleMuokkaaPelaaja() {
        muokkaaPelaaja();
    }
      
      
    @FXML private void handlePoistapelaaja() {
        poistaPelaaja();
    }
      
      
    @FXML private void handlePoistajoukkue() {
        poistaJoukkue();
    }
      
      
    @FXML private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null); 
        tulostaValitut(tulostusCtrl.getTextArea()); 
    }
      
      
    @FXML private void handleTieto() {
        naytaTiedot();
    }
    
    
    @FXML private void handleHakuehto() {
        hae(0);
    }


      
//===========================================================================================    
// Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia    
    private Joukkuerekisteri joukkuerekisteri;
    private TextField[] edits;
    private Joukkue joukkueKohdalla;
    private Pelaaja pelaajaKohdalla;
    private static Joukkue apujoukkue = new Joukkue();
    private String sarjannimi = "Keski-Suomen 8. divisioona";
      
     
    
    /**
     * Tehdään tarvittavat alustukset, kuten kuuntelijoiden alustukset
     */
    private void alusta() {
        chooserJoukkueet.clear();
        chooserJoukkueet.addSelectionListener(e -> naytaJoukkue());
        chooserPelaajat.clear();
        chooserPelaajat.addSelectionListener(e -> naytaPelaaja());
        
        cbEhdot.clear(); 
        for (int k = apujoukkue.ekaKentta(); k < apujoukkue.getKenttia(); k++) 
            cbEhdot.add(apujoukkue.getKysymys(k), null); 
        cbEhdot.getSelectionModel().select(0);
         
        edits = new TextField[]{textYksi, textKaksi, textKolme, textNelja, textViisi, textKuusi, textSeitseman, textKahdeksan}; 
    }
     
     
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = SarjanNimiController.kysyNimi(null, sarjannimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    
    /**
     * Alustaa joukkuerekisterin lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta joukkuerekisterin tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
        sarjannimi = nimi;
        try {
            joukkuerekisteri.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
      
     
    /**
     * Tietojen tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            joukkuerekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }

     
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
     
     
    /**
     * Näyttää listasta valitun joukkueen tiedot
     */
    private void naytaJoukkue() {
        joukkueKohdalla = chooserJoukkueet.getSelectedObject();
        if (joukkueKohdalla == null) return;
        textYksi.setText(joukkueKohdalla.getNimi());
        textKaksi.setText(joukkueKohdalla.getKotipaikkakunta());
        textKolme.setText(joukkueKohdalla.getVuosi());
        textNelja.setText(joukkueKohdalla.getKotikentta());
        textViisi.setText(String.valueOf(joukkuerekisteri.annaPelaajat(joukkueKohdalla).size()));
        textKuusi.setText(joukkuerekisteri.KPituus(joukkueKohdalla));
        textSeitseman.setText(joukkuerekisteri.KPaino(joukkueKohdalla));
        textKahdeksan.setText(joukkuerekisteri.KIka(joukkueKohdalla));
        labelYksi.setText("Nimi:");
        labelKaksi.setText("Kotipaikkakunta:");
        labelKolme.setText("Perustusvuosi:");
        labelNelja.setText("Kotikenttä:");
        labelViisi.setText("Pelaajien määrä:");
        labelKuusi.setText("Keskipituus:");
        labelSeitseman.setText("Keskipaino:");
        labelKahdeksan.setText("Keski-ikä:");
        
        MuokkaaJoukkueController.naytaJoukkue(edits, joukkueKohdalla);
        naytaPelaajat(joukkueKohdalla);
    }
    
     
     
     
    /**
     * Näyttää listasta valitun pelaajan tiedot
     */
    private void naytaPelaaja() {
        pelaajaKohdalla = chooserPelaajat.getSelectedObject();
        if (pelaajaKohdalla == null) return;
        textYksi.setText(pelaajaKohdalla.getNimi());
        textKaksi.setText(pelaajaKohdalla.getSyntyma());
        textKolme.setText(pelaajaKohdalla.getKansalaisuus());
        textNelja.setText(joukkueKohdalla.getNimi());
        textViisi.setText(pelaajaKohdalla.getPelinumero());
        textKuusi.setText(pelaajaKohdalla.getPelipaikka());
        textSeitseman.setText(Double.toString(pelaajaKohdalla.getPituus()));
        textKahdeksan.setText(Double.toString(pelaajaKohdalla.getPaino()));
        labelYksi.setText("Nimi:");
        labelKaksi.setText("Syntymäaika:");
        labelKolme.setText("Kansalaisuus:");
        labelNelja.setText("Joukkue:");
        labelViisi.setText("Pelinumero:");
        labelKuusi.setText("Pelipaikka:");
        labelSeitseman.setText("Pituus:");
        labelKahdeksan.setText("Paino:");
    }
     
     
    /**
     * Näyttää joukkueen pelaajat listalla
     * @param joukkue käytettävä joukkue
     */
    private void naytaPelaajat(Joukkue joukkue) {
        chooserPelaajat.clear();
        if ( joukkue == null ) return;
                 
        List<Pelaaja> pelaajat = joukkuerekisteri.annaPelaajat(joukkue);
        if ( pelaajat.size() == 0 ) return;
        for (Pelaaja p: pelaajat) {
            chooserPelaajat.add(p.getNimi(), p);
        }
    }
     
     
    /**
     * Avaa näkymän, jossa tietoa sarjasta
     */
    private void naytaTiedot() {
        ModalController.showModal(JoukkuerekisteriGUIController.class.getResource("tietoasarjasta.fxml"), "Tietoa sarjasta", null, joukkuerekisteri);
    }
     
     
    /**
     * Poistetaan listalta valittu joukkue
     */
    private void poistaJoukkue() {
        Joukkue joukkue = joukkueKohdalla;
        if ( joukkue == null ) return;
        if ( !Dialogs.showQuestionDialog("Poista joukkue", "Haluatko varmasti poistaa joukkueen: " + joukkue.getNimi(), "Kyllä", "Ei") )
            return;
        joukkuerekisteri.poista(joukkue);
        int index = chooserJoukkueet.getSelectedIndex();
        hae(0);
        chooserJoukkueet.setSelectedIndex(index);
    }
     
     
    /**
     * Poistetaan listasta valittu pelaaja 
     */
    private void poistaPelaaja() {
        pelaajaKohdalla = chooserPelaajat.getSelectedObject();
        Pelaaja pelaaja = pelaajaKohdalla;
        if ( pelaaja == null ) return;
        if ( !Dialogs.showQuestionDialog("Poista pelaaja", "Haluatko varmasti poistaa pelaajan: " + pelaaja.getNimi(), "Kyllä", "Ei") )
            return;
        joukkuerekisteri.poistaPelaaja(pelaaja);
        int index = chooserPelaajat.getSelectedIndex();
        hae(0);
        chooserPelaajat.setSelectedIndex(index);
    }
     
    
    /**
     * Luo uuden joukkueen, jonka tietoja aletaan asettamaan
     */
    private void lisaaJoukkue() {
        try {
            Joukkue uusi = new Joukkue();
            uusi = MuokkaaJoukkueController.kysyJoukkue(null, uusi);  
            if ( uusi == null ) return;
            uusi.rekisteroi();
            joukkuerekisteri.lisaa(uusi);
            hae(uusi.getTunnusNro());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
    }
     
    
    /**
     * Hakee joukkueiden tiedot listaam
     * @param jnr joukkueen numero, joka aktivoidaan haun jälkeen
     */
    private void hae(int jnr) {     
        int jnro = jnr; 
        if ( jnro <= 0 ) { 
            Joukkue kohdalla = joukkueKohdalla; 
            if ( kohdalla != null ) jnro = kohdalla.getTunnusNro(); 
        }
                
        int k = cbEhdot.getSelectionModel().getSelectedIndex() + apujoukkue.ekaKentta(); 
        String ehto = textHakuehto.getText(); 
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
        
        chooserJoukkueet.clear();
        
        int index = 0;
        Collection<Joukkue> joukkueet;
        joukkueet = joukkuerekisteri.etsi(ehto, k);
        int i = 0;
        for (Joukkue joukkue:joukkueet) {
            if (joukkue.getTunnusNro() == jnro) index = i;
            chooserJoukkueet.add(joukkue.getNimi(), joukkue);
            i++;
        }
        chooserJoukkueet.setSelectedIndex(index);
    }
    
     
     
    /**
     * @param joukkuerekisteri Sarja, jota käytetään tässä käyttöliittymässä
     */
    public void setJoukkuerekisteri(Joukkuerekisteri joukkuerekisteri) {
        this.joukkuerekisteri = joukkuerekisteri;
        naytaJoukkue();
    }
    
     
    /**
     * Luo uuden joukkueen, jonka tietoja aletaan asettamaan 
     */
    public void lisaaPelaaja() { 
        try {
            Pelaaja uusi = new Pelaaja();
            uusi = MuokkaaPelaajaController.kysyPelaaja(null, uusi);  
            if ( uusi == null ) return;
            uusi.setJoukkueenro(joukkueKohdalla.getTunnusNro());
            uusi.rekisteroi();
            joukkuerekisteri.lisaa(uusi);
            hae(joukkueKohdalla.getTunnusNro());
            naytaPelaaja();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
    }
     
    
    /**
     * Muutetaan joukkueen tietoja. Avaa näkymän muokkauksia varten
     */
    private void muokkaa() { 
        if ( joukkueKohdalla == null ) return; 
        try { 
            Joukkue joukkue; 
            joukkue = MuokkaaJoukkueController.kysyJoukkue(null, joukkueKohdalla.clone()); 
            if ( joukkue == null ) return; 
            joukkuerekisteri.korvaaTaiLisaa(joukkue); 
            hae(joukkue.getTunnusNro()); 
        } catch (CloneNotSupportedException e) { 
          // 
        } catch (SailoException e) { 
            Dialogs.showMessageDialog(e.getMessage()); 
        } 
    }     
     
    
    /**
     * Muutetaan pelaajan tietoja. Avaa näkymän muokkauksia varten
     */
    private void muokkaaPelaaja() { 
        if ( pelaajaKohdalla == null ) return; 
        try { 
            Pelaaja pelaaja; 
            pelaaja = MuokkaaPelaajaController.kysyPelaaja(null, pelaajaKohdalla.clone()); 
            if ( pelaaja == null ) return; 
            joukkuerekisteri.korvaaTaiLisaa(pelaaja); 
            hae(pelaaja.getTunnusNro()); 
        } catch (CloneNotSupportedException e) { 
             // 
        } catch (SailoException e) { 
            Dialogs.showMessageDialog(e.getMessage()); 
        }
    }
            
     
    @Override
    public String getResult() {
        // TODO Auto-generated method stu
        return null;
    }


    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }
    
    
    /**
     * Tulostaa joukkueen tiedot
     * @param os tietovirta johon tulostetaan
     * @param joukkue tulostettava joukkue
     */
    public void tulosta(PrintStream os, final Joukkue joukkue) {
        os.println("----------------------------------------------");
        joukkue.tulosta(os);
        os.println("----------------------------------------------");
        List<Pelaaja> pelaajat = joukkuerekisteri.annaPelaajat(joukkue);
        for (Pelaaja pelaaja:pelaajat) 
            pelaaja.tulosta(os);      
    }
        
        
    /**
     * Tulostaa listassa olevat joukkueet tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan valitut joukkueet");
            for (Joukkue joukkue: chooserJoukkueet.getObjects()) { 
                tulosta(os, joukkue);
                os.println("\n\n");
            }
        }
    }
}