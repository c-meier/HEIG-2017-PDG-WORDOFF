/*
 * File: LoginController.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */
package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.UserApi;
import ch.heigvd.wordoff.client.Exception.BadRequestException;
import ch.heigvd.wordoff.client.Exception.UnauthorizedException;
import ch.heigvd.wordoff.client.Exception.UnprocessableEntityException;
import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.common.Dto.User.LoginDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import org.springframework.web.client.ResourceAccessException;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the login screen. Allows a user to login or signup.
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
    }

    /**
     * Called by the login button to attempt to sign in. Displays an error if
     * username or password field is empty or if the connection to the server
     * failed.
     */
    @FXML
    private void signIn(ActionEvent event) {
        if (!nameOrPasswordIsEmpty()) {
            try {
                String name = userName.getText();
                char[] pass = passWord.getText().toCharArray();

                UserApi.signIn(new LoginDto(name, pass));
                handleGoToMainMenu();
            } catch (BadRequestException e) {
                e.printStackTrace();
            } catch (UnauthorizedException e) {
                Dialog.getInstance().signalInformation("Nom d'utilisateur ou mot de passe invalide");
            } catch (HTTPException e) {
                Dialog.getInstance().signalInformation("Une erreur s'est produite");
            } catch (ResourceAccessException e) {
                Dialog.getInstance().signalError("Connection au serveur impossible.");
            }

        }
    }

    /**
     * Called by the signup button to attempt to signup. Signals an error if
     * username or password field is empty, server cannot be accessed or if
     * the username is already taken.
     *
     * @param event
     */
    @FXML
    private void signUp(ActionEvent event) {
        if (!nameOrPasswordIsEmpty()) {
            try {
                String name = userName.getText();
                char[] pass = passWord.getText().toCharArray();
                LoginDto loginDto = new LoginDto(name, pass);

                UserApi.signUp(loginDto);
                Dialog.getInstance().signalInformation("Utilisateur " + name + " créé!");
                UserApi.signIn(loginDto);

                handleGoToMainMenu();
            } catch (BadRequestException e) {
                Dialog.getInstance().signalInformation("Une erreur s'est produite " + e.getMessage());
                //e.printStackTrace();
            } catch (UnprocessableEntityException e) {
                Dialog.getInstance().signalInformation("L'utilisateur existe déjà! Veuillez entrer un autre nom.");
                //e.printStackTrace();
            } catch (HTTPException e) {
                // e.printStackTrace();
                Dialog.getInstance().signalInformation("Une erreur s'est produite " + e.getMessage());
            } catch (ResourceAccessException e) {
                Dialog.getInstance().signalError("Connection au serveur impossible.");
            }
        }
    }

    /**
     * Checks if username or password field is empty
     *
     * @return true if empty
     */
    private boolean nameOrPasswordIsEmpty() {
        if (userName.getText().isEmpty() || passWord.getText().isEmpty()) {
            Dialog.getInstance().signalInformation("Entrez un nom d'utilisateur et un mot de passe!");
            return true;
        }
        return false;
    }

    /**
     * Proceeds to main menu screen.
     */
    private void handleGoToMainMenu() {
        String pathContoller = "/fxml/mainMenu.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pathContoller));
        Scene scene = getScene(loader);

        MainMenuController controller = loader.getController();
        controller.setState();

        MainApp.changeScene(scene);
    }

    // Creates loader scene
    private Scene getScene(FXMLLoader loader) {
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {

        }
        return new Scene(root);
    }


}
