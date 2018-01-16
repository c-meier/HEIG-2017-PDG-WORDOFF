/*
 * File: ListCustom.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Util;

import ch.heigvd.wordoff.common.Dto.Mode.ModeSummaryDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Custom list.
 */
public class ListCustom {

    private VBox vBox;
    private ListView<String> listView;
    private ObservableList<String> listGames; // list of games
    private List<Image> listImages; // list of avatar
    private List<ModeSummaryDto> listDto;

    // Default image for the avatar
    private String url = "images/vs_logo.png";
    private final Image IMG = new Image(url);

    /**
     * Constructor
     * @param vbox vbox associated with the game list
     */
    public ListCustom(VBox vbox) {
        this.vBox = vbox;
        this.listImages = new LinkedList<>();
        this.listView = new ListView<String>();
        this.listGames = FXCollections.observableArrayList();
        this.listView.setItems(listGames);
        this.listDto = new ArrayList<>();

        this.vBox.getChildren().add(listView);
        this.vBox.setAlignment(Pos.CENTER);
        updateView();
    }

    /**
     * Add a list of games with a default image of the player's avatar
     * @param list list of games
     */
    public void addGamesList(List<ModeSummaryDto> list) {
        for (ModeSummaryDto game : list) {
            listImages.add(IMG); // default image
            listGames.add(game.getName().toUpperCase());
        }
    }

    /**
     * Add a game with a default image of the player's avatar
     * @param game game to add
     */
    public void addGame(ModeSummaryDto game) {
        listImages.add(IMG);
        listGames.add(game.getName());
        listDto.add(game);
    }

    /**
     * Returns list of DTOs associated with this list
     * @return
     */
    public List<ModeSummaryDto> getDtos(){
        return listDto;
    }

    /**
     * Return the vBox associated with the game list
     * @return vBox
     */
    public VBox getvBox() {
        return vBox;
    }

    /**
     * Return the ListView of the games
     * @return listView of the games
     */
    public ListView<String> getListView() {
        return listView;
    }

    /**
     * Initialization of the format of the cells for the list of games.
     */
    private void updateView() {
        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(listImages.get(getIndex()));
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
    }

    public void clear(){
        listDto.clear();
        listGames.clear();
        listImages.clear();
    }
}
