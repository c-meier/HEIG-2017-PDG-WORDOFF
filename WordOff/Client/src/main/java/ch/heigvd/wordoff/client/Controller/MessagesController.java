package ch.heigvd.wordoff.client.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import ch.heigvd.wordoff.client.Util.UtilChangeScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class MessagesController implements Initializable {

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        UtilChangeScene.getInstance().handleGotoMenu();
    }
    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
