package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class AlertController implements Initializable {

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        String controller = "/fxml/mainMenu.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller));
        MainApp.changeScene(loader);
    }

    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
