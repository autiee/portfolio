package fxJoukkuerekisteri;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import joukkuerekisteri.*;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author eelia
 * @version 30.7.2020
 *
 */
public class JoukkuerekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("JoukkuerekisteriGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final JoukkuerekisteriGUIController joukkuerekisteriCtrl = (JoukkuerekisteriGUIController) ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("joukkuerekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Joukkuerekisteri");
            
            primaryStage.setOnCloseRequest((event) -> {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
                if ( !joukkuerekisteriCtrl.voikoSulkea() ) event.consume();
            });
            
            Joukkuerekisteri joukkuerekisteri = new Joukkuerekisteri();
            joukkuerekisteriCtrl.setJoukkuerekisteri(joukkuerekisteri);
            
            primaryStage.show();
            
            Application.Parameters params = getParameters(); 
            if ( params.getRaw().size() > 0 ) 
                joukkuerekisteriCtrl.lueTiedosto(params.getRaw().get(0));  
            else
                if ( !joukkuerekisteriCtrl.avaa() ) Platform.exit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}