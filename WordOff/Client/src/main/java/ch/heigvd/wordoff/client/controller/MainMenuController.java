package ch.heigvd.wordoff.client.controller;

import ch.heigvd.wordoff.client.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.heigvd.wordoff.client.logic.Game;
import ch.heigvd.wordoff.common.Dto.GameDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainMenuController implements Initializable {

    // Game selected to list
    private GameDto selectGame = null;
    
    private void handleGotoGame() {
        FXMLLoader loader = getLoader("/fxml/gameScreen.fxml");
        Scene scene = getScene(loader);

        GameScreenController controller = loader.getController();
        controller.setGame(this.selectGame);

        changeScene(scene);
    }

    @FXML
    private void handleGotoProfile(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/profile.fxml");
        changeScene(getScene(loader));
    }
    
    @FXML
    private void handleGotoFriends(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/friends.fxml");
        changeScene(getScene(loader));
    }
    
    @FXML
    private void handleGotoMessages(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/messages.fxml");
        changeScene(getScene(loader));
    }
    
    @FXML
    private void handleGotoSettings(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/settings.fxml");
        changeScene(getScene(loader));
    }
    
    @FXML
    private void handleGotoInvitations(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/invitations.fxml");
        changeScene(getScene(loader));
    }
    
    @FXML
    private void handleGotoAlert(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/alertes.fxml");
        changeScene(getScene(loader));
    }

    // Envoi la scène au MainApp pour changer la scene dans le stage
    private void changeScene(Scene scene) {
        MainApp.changeScene(scene);
    }

    // Récupère le fxml loader depuis le path
    private FXMLLoader getLoader(String fxmlPath){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        return loader;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToGame(){
        // Récupérer le game dans la liste
        this.selectGame = (new Game()).getGameDto();
        handleGotoGame();
    }

}
