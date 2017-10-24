package ch.heigvd.wordoff.client.controller;


import ch.heigvd.wordoff.client.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

/**
 *
 * @author Gabriel Luthier
 */
public class GameScreenController implements Initializable {

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        String controller = "/fxml/mainMenu.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller));
        MainApp.changeScene(controller, loader);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
