package ch.heigvd.wordoff.client.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for a game Tile
 */
public class TileController extends AnchorPane implements Initializable{

    @FXML
    private Label letter;

    @FXML
    private Label score;
    
    private String letterValue;
    private int scoreValue;
    
    public TileController(String letterValue, int scoreValue){
        this.letterValue = letterValue;
        this.scoreValue = scoreValue;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score.setText(String.valueOf(scoreValue));
        letter.setText(letterValue);
    }
    
}
