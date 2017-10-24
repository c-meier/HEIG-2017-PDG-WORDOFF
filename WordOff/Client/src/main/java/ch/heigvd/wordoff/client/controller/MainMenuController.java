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
    private void handleGotoInvitationsRecieved(ActionEvent event) {
        changeScene("/fxml/invitationsRecieved.fxml");
    }
    
    @FXML
    private void handleGotoInvitationsSent(ActionEvent event) {
        changeScene("/fxml/invitationsSent.fxml");
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
