/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.UserApi;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.common.Dto.LoginDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Eddie
 */
public class LoginController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private TextField passWord;
    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void handleGoToMainMenu(ActionEvent event){

        if(userName.getText().isEmpty() || passWord.getText().isEmpty()){
            Dialog.getInstance().signalInformation("Please enter your userName and passWord");
        }else{
            String name = userName.getText();
            char[] pass = passWord.getText().toCharArray();
            UserApi.signIn(new LoginDto(name,pass));

            String pathContoller = "/fxml/mainMenu.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathContoller));
            Scene scene = getScene(loader);

            MainMenuController controller = loader.getController();
            controller.setState();

            MainApp.changeScene(scene);
        }
    }

    // Création de la scène du loader
    private Scene getScene(FXMLLoader loader){
        Parent root = null;
        try {
            root = loader.load();
        }catch (IOException e){

        }
        return new Scene(root);
    }
    
    
    
}
