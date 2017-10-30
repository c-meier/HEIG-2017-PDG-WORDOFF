package ch.heigvd.wordoff.client.controller;


import ch.heigvd.wordoff.client.MainApp;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ch.heigvd.wordoff.client.Model.ISlot;
import ch.heigvd.wordoff.client.logic.Challenge;
import ch.heigvd.wordoff.client.logic.Game;
import ch.heigvd.wordoff.client.logic.Side;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.*;

import javax.swing.text.LabelView;

/**
 * @author Gabriel Luthier
 */
public class GameScreenController implements Initializable {

    private Game game;
    @FXML
    private Label p1Name, p2Name;
    // emplacement image

    private List<AnchorPane> p1TilesPr = new ArrayList<>();
    private List<AnchorPane> p1TilesSr = new ArrayList<>();
    private List<AnchorPane> p1SlotsCh = new ArrayList<>();
    private List<AnchorPane> p1SlotsSr = new ArrayList<>();
    private List<AnchorPane> p1SlotsPr = new ArrayList<>();
    private List<AnchorPane> p2SlotsCh = new ArrayList<>();
    private List<AnchorPane> p2TilesPr = new ArrayList<>();
    private List<AnchorPane> p2TilesSr = new ArrayList<>();
    private List<AnchorPane> p2SlotsSr = new ArrayList<>();
    private List<AnchorPane> p2SlotsPr = new ArrayList<>();

    @FXML
    private AnchorPane p1Ch1Back, p1Ch2Back, p1Ch3Back, p1Ch4Back, p1Ch5Back, p1Ch6Back, p1Ch7Back;
    @FXML
    private AnchorPane p1Ch1Fore, p1Ch2Fore, p1Ch3Fore, p1Ch4Fore, p1Ch5Fore, p1Ch6Fore, p1Ch7Fore;
    //  @FXML
//    private AnchorPane p1TileSr1, p1TileSr2;
    @FXML
    private AnchorPane p1TilePr1, p1TilePr2, p1TilePr3, p1TilePr4, p1TilePr5, p1TilePr6, p1TilePr7;

    @FXML
    AnchorPane p2Ch1Back, p2Ch2Back, p2Ch3Back, p2Ch4Back, p2Ch5Back, p2Ch6Back, p2Ch7Back;
    @FXML
    AnchorPane p2Ch1Fore, p2Ch2Fore, p2Ch3Fore, p2Ch4Fore, p2Ch5Fore, p2Ch6Fore, p2Ch7Fore;
    //  @FXML
//    private AnchorPane p2TileSr1, p2TileSr2;
    @FXML
    private AnchorPane p2TilePr1, p2TilePr2, p2TilePr3, p2TilePr4, p2TilePr5, p2TilePr6, p2TilePr7;

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        String controller = "/fxml/mainMenu.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller));
        MainApp.changeScene(controller, loader);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setState(new Game());
    }

    /**
     * Recoit le side lié à la game
     *
     * @param game
     */
    public void setState(Game game) {
        this.game = game;
        Side sideP1 = game.getSideP1();
        Side sideP2 = game.getSideP2();

   /*   p1Name.setText(sideP1.getPlayer().getName());
        p2Name.setText(sideP2.getPlayer().getName());*/
        p1SlotsCh.add(p1Ch1Back);
        p1SlotsCh.add(p1Ch2Back);
        p1SlotsCh.add(p1Ch3Back);
        p1SlotsCh.add(p1Ch4Back);
        p1SlotsCh.add(p1Ch5Back);
        p1SlotsCh.add(p1Ch6Back);
        p1SlotsCh.add(p1Ch7Back);

        p2SlotsCh.add(p2Ch1Back);
        p2SlotsCh.add(p2Ch2Back);
        p2SlotsCh.add(p2Ch3Back);
        p2SlotsCh.add(p2Ch4Back);
        p2SlotsCh.add(p2Ch5Back);
        p2SlotsCh.add(p2Ch6Back);
        p2SlotsCh.add(p2Ch7Back);

        p1TilesPr.add(p1TilePr1);
        p1TilesPr.add(p1TilePr2);
        p1TilesPr.add(p1TilePr3);
        p1TilesPr.add(p1TilePr4);
        p1TilesPr.add(p1TilePr5);
        p1TilesPr.add(p1TilePr6);
        p1TilesPr.add(p1TilePr7);

        p2TilesPr.add(p2TilePr1);
        p2TilesPr.add(p2TilePr2);
        p2TilesPr.add(p2TilePr3);
        p2TilesPr.add(p2TilePr4);
        p2TilesPr.add(p2TilePr5);
        p2TilesPr.add(p2TilePr6);
        p2TilesPr.add(p2TilePr7);

        //  p1TilesSr.add(p1TileSr1);
//        p1TilesSr.add(p1TileSr2);

        // refresh les slots du challenge (image de fond)
        refreshSlots(sideP1, p1SlotsCh);
        refreshSlots(sideP2, p2SlotsCh);

        // refresh le contenus des Tiles GUI et on les attaches au slots
        refreshTiles(sideP1.getPlayerRack().getRack(), p1SlotsPr, p1TilesPr);
        //  refreshTiles(sideP1.getSwapRack().getRack(), p1SlotsSr, p1TilesSr);
        refreshTiles(sideP2.getPlayerRack().getRack(), p2SlotsPr, p2TilesPr);
        //refreshTiles(sideP2.getSwapRack().getRack(), p2SlotsSr, p2TilesSr);

        addTileToSlot(p1Ch1Fore,p1TilePr1);
        addTileToSlot(p1Ch1Fore,p1TilePr2);
    }

    @FXML
    private void refreshSlots(Side side, List<AnchorPane> slots) {
        // Placer les images du challenge selon les slots
        int i = 0;
        for (ISlot slot : side.getChallenge().getSlots()) {
            slots.get(i++).setBackground(new Background(new BackgroundImage(slot.getImage(),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null)));
        }
    }

    @FXML
    private void refreshTiles(List<Tile> rack, List<AnchorPane> slots, List<AnchorPane> tiles) {
        // Initialiser le contenu des objets Tiles de la GUI selon l'état des racks
        int i = 0;
        for (Tile tile : rack) {
            javafx.scene.control.Label value = (javafx.scene.control.Label) tiles.get(i).getChildren().get(0);
            javafx.scene.control.Label score = (javafx.scene.control.Label) tiles.get(i++).getChildren().get(1);
            value.setText(String.valueOf(tile.getValue()).toUpperCase());
            score.setText(String.valueOf(tile.getScore()));
        }
    }

    // Ajoute la tile dans un slot
    @FXML
    private void addTileToSlot(AnchorPane slot, AnchorPane tile) {
        if(slot.getChildren().isEmpty())
             slot.getChildren().add(tile);
    }

}

