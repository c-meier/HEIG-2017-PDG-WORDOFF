package ch.heigvd.wordoff.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class tileController extends AnchorPane implements Initializable{

    @FXML
    private Label letter;

    @FXML
    private Label score;
    
    private String letterValue;
    private int scoreValue;
    
    public tileController(String letterValue, int scoreValue){
        this.letterValue = letterValue;
        this.scoreValue = scoreValue;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score.setText(score.toString());
        letter.setText(letterValue);
    }
    
}
