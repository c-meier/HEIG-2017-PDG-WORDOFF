package ch.heigvd.wordoff.controller;

import ch.heigvd.wordoff.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneController));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            MainApp.getStage().setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
