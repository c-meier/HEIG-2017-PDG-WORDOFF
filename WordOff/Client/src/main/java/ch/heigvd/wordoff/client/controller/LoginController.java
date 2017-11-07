/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.wordoff.client.controller;

import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.common.Model.Challenge;
import ch.heigvd.wordoff.common.Model.Slots.Slot;

import java.net.URL;
import java.util.ArrayList;
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
        
        MainApp.getApiClient().play(Long.MAX_VALUE, new Challenge(new ArrayList<Slot>()));
    }
    
    @FXML
    private void handleGoToMainMenu(ActionEvent event){
        String controller = "/fxml/mainMenu.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller));
        MainApp.changeScene(controller, loader);
    }
    
    
    
}
