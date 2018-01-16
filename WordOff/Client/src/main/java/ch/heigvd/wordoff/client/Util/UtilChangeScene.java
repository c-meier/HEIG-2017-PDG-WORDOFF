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
 * Helper to change the scene of the GUI.
 */
public class UtilChangeScene {

   private static UtilChangeScene INSTANCE = new UtilChangeScene();

   private UtilChangeScene(){}

   public static UtilChangeScene getInstance(){
       return INSTANCE;
   }

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
