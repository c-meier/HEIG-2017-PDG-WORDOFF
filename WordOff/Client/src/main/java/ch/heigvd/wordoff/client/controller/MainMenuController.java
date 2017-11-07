package ch.heigvd.wordoff.client.controller;

import ch.heigvd.wordoff.client.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class MainMenuController implements Initializable {
    
    @FXML
    private void handleGotoGame(ActionEvent event) {
        changeScene("/fxml/gameScreen.fxml");
    }

    @FXML
    private void handleGotoProfile(ActionEvent event) {
        changeScene("/fxml/profile.fxml");
    }
    
    @FXML
    private void handleGotoFriends(ActionEvent event) {
        changeScene("/fxml/friends.fxml");
    }
    
    @FXML
    private void handleGotoMessages(ActionEvent event) {
        changeScene("/fxml/messages.fxml");
    }
    
    @FXML
    private void handleGotoSettings(ActionEvent event) {
        changeScene("/fxml/settings.fxml");
    }
    
    @FXML
    private void handleGotoInvitations(ActionEvent event) {
        changeScene("/fxml/invitations.fxml");
    }
    
    @FXML
    private void handleGotoAlert(ActionEvent event) {
        changeScene("/fxml/alertes.fxml");
    }
    
    private void changeScene(String sceneController) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneController));
        MainApp.changeScene(sceneController, loader);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


}
