/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.wordoff.client.controller;

import ch.heigvd.wordoff.client.MainApp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Eddie
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void handleGoToMainMenu(ActionEvent event){
        String pathContoller = "/fxml/mainMenu.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pathContoller));

  //      MainMenuController controller = loader.getController();
//        controller.setState();
        MainApp.changeScene(loader);
    }
    
    
    
}
