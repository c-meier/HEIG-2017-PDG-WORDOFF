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

import java.util.LinkedList;
import java.util.List;

public class ListCustom {

    private VBox vBox;
    private ListView<String> listView;
    private ObservableList<String> items;
    private List<Image> listImages;

    private final Image IMAGE_RUBY = new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Ruby_logo_64x64.png");

    public ListCustom(VBox vbox){
        this.vBox = vbox;
        this.listImages = new LinkedList<>();
        this.listView = new ListView<String>();
        this.items = FXCollections.observableArrayList();
        this.listView.setItems(items);

        this.vBox.getChildren().add(listView);
        this.vBox.setAlignment(Pos.CENTER);
    }

    public void addGamesList(List<ModeSummaryDto> listGames) {
        for (ModeSummaryDto game : listGames) {
            // TODO récupérer l'image de l'adversaire
            //listImagesGames.add(game.getOtherPlayer().getImage());
            listImages.add(IMAGE_RUBY);
            items.add(game.getName().toUpperCase());
        }
    }

    public void addGamesListAndUpdate(List<ModeSummaryDto> listGames){
        addGamesList(listGames);
        updateView();
    }

    public void addGame(ModeSummaryDto game){
        items.add(game.getName());
        // TODO récupérer l'image de l'adversaire
        //listImages.add(game.getOtherPlayer().getImage());
        listImages.add(IMAGE_RUBY);
    }
    public void addGameAndUpdate(ModeSummaryDto game){
        addGame(game);
        updateView();
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setState(int index, String game) {
        items.set(index, game);
    }

    public ListView<String> getListView() {
        return listView;
    }

    public void updateView() {
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
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
    }
}
