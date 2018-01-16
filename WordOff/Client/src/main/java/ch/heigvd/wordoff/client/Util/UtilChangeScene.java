/*
 * File: UtilChangeScene.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Util;

import ch.heigvd.wordoff.client.Controller.MainMenuController;
import ch.heigvd.wordoff.client.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Utility class for controllers. This class is a singleton and returns to the main menu
 */
public class UtilChangeScene {

    /**
     * private constructor
     */
    private UtilChangeScene(){}

    /**
     * Single instance of the classe
     */
   private static UtilChangeScene INSTANCE = new UtilChangeScene();

    /**
     * Return the single instance of the classe
     */
   public static UtilChangeScene getInstance(){
       return INSTANCE;
   }

    /**
     * Loading the main menu window
     */
   public void handleGotoMenu(){
       String controllerPath = "/fxml/mainMenu.fxml";
       FXMLLoader loader = new FXMLLoader(getClass().getResource(controllerPath));
       Parent root = null;
       try {
           root = loader.load();
           Scene scene = new Scene(root);
           MainMenuController controller = loader.getController();
           controller.setState();
           MainApp.changeScene(scene);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }


}
