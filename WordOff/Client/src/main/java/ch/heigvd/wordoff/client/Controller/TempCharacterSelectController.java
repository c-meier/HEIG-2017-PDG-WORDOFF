package ch.heigvd.wordoff.client.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TempCharacterSelectController extends ScrollPane implements Initializable{
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
        grid.getChildren().add(new TempTileController("A", 1));
        characters.setContent(grid);
        for(Character c : alphabet){
            
        }

    }    


}
