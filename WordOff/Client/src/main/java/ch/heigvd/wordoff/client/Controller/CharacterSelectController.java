package ch.heigvd.wordoff.client.Controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CharacterSelectController extends BorderPane implements Initializable{
    private static final int GRID_WIDTH = 9;

    @FXML
    private ScrollPane characters;
    
    @FXML
    private Button closeButton;
    
    String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    List<Character> alphabet = alphabetString
            .chars()
            .mapToObj(e->(char)e)
            .collect(Collectors.toList());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final int height = Math.floorDiv(alphabet.size(), GRID_WIDTH) + 1;
        GridPane grid = new GridPane();
        int i = 0;
        for(Character c : alphabet){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tile.fxml"));     
            try {
                TileController tc = new TileController(String.valueOf(c), 0);
                fxmlLoader.setController(c);
                AnchorPane tile = fxmlLoader.load();
                grid.add(tile, i/GRID_WIDTH, i % GRID_WIDTH);
                characters.setContent(grid);
            } catch (IOException ex) {
                Logger.getLogger(CharacterSelectController.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++;
        }
    }

    
    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
