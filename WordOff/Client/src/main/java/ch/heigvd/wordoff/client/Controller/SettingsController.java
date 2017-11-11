package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

import ch.heigvd.wordoff.client.Util.GoToMainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class SettingsController implements Initializable {

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        GoToMainMenu.getInstance().handleGotoMenu();
    }

    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
