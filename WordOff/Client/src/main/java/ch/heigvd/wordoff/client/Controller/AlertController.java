package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Util.UtilChangeScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class AlertController implements Initializable {

    @FXML
    ListView listNewAlert;
    @FXML
    ListView listHistAlert;

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        UtilChangeScene.getInstance().handleGotoMenu();
    }

    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listNewAlert.getItems().addAll("Aucune alerte");
        listHistAlert.getItems().addAll("Aucune alerte");
    }    
    
}
