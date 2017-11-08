/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.wordoff.client.controller;

import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.client.api.UserApi;
import ch.heigvd.wordoff.common.Dto.LoginDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

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

        LoginDto loginDto = new LoginDto("one", "pass".toCharArray());

        String token = UserApi.signIn(loginDto);
    }
    
    @FXML
    private void handleGoToMainMenu(ActionEvent event){
        String controller = "/fxml/mainMenu.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller));
        MainApp.changeScene(controller, loader);
    }
    
    
    
}
