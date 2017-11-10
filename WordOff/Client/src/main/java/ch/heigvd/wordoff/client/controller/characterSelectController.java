package ch.heigvd.wordoff.client.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class characterSelectController extends ScrollPane implements Initializable{
    private static final int GRID_WIDTH = 9;
    
    @FXML
    private ScrollPane characters;
    
    String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    List<Character> alphabet = alphabetString
            .chars()
            .mapToObj(e->(char)e)
            .collect(Collectors.toList());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final int height = Math.floorDiv(alphabet.size(), GRID_WIDTH) + 1;
        GridPane grid = new GridPane();
        grid.getChildren().add(new tileController("A", 1));
        characters.setContent(grid);
        for(Character c : alphabet){
            
        }

    }    


}
