package ch.heigvd.wordoff.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        MainApp.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("WordOff");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.sizeToScene();
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main()
     * serves only as fallback in case the application can not be launched through
     * deployment artifacts, e.g., in IDEs with limited FX support. NetBeans ignores
     * main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScene(String sceneController, FXMLLoader loader) {
        try {
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
