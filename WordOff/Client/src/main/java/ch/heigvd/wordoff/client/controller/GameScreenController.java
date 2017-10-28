package ch.heigvd.wordoff.client.controller;


import ch.heigvd.wordoff.client.MainApp;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ch.heigvd.wordoff.client.Model.ISlot;
import ch.heigvd.wordoff.client.logic.Game;
import ch.heigvd.wordoff.client.logic.Side;

import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.*;

/**
 * @author Gabriel Luthier
 */
public class GameScreenController implements Initializable {

    private Game game;
    @FXML
    private Label p1Name, p2Name;
    // emplacement image

    private List<AnchorPane> p1SlotsCh, p1SlotsSr, p1SlotsPr, p1TilesPr, p1TilesSr;
    private List<AnchorPane> p2SlotsCh, p2SlotsSr, p2SlotsPr, p2TilesPr, p2TilesSr;
    // Tiles
    // Contient 2 labels , 1er = score, 2eme = lettre
    @FXML
    private AnchorPane p1TileSr1, p1TileSr2;
    @FXML
    private AnchorPane p2TileSr1, p2TileSr2;
    @FXML
    private AnchorPane p1TilePr1, p1TilePr2, p1TilePr3, p1TilePr4, p1TilePr5, p1TilePr6, p1TilePr7;
    @FXML
    private AnchorPane p2TilePr1, p2TilePr2, p2TilePr3, p2TilePr4, p2TilePr5, p2TilePr6, p2TilePr7;

    // Slots Sp = swap Rack, Ch = Challenge
    // Un rack contient un AnchorPane où les Tiles vont pouvoir s'insérer
    @FXML
    private AnchorPane p1Sr1, p1Sr2;
    @FXML
    private AnchorPane p2Sr1, p2Sr2;
    @FXML
    private AnchorPane p1Ch1, p1Ch2, p1Ch3, p1Ch4, p1Ch5, p1Ch6, p1Ch7;
    @FXML
    private AnchorPane p2Ch1, p2Ch2, p2Ch3, p2Ch4, p2Ch5, p2Ch6, p2Ch7;

    // Grid for rack p1 = ligne 3, p2 = ligne 1
    @FXML
    private GridLayout p1pr;
    @FXML
    private GridLayout p2pr;

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        String controller = "/fxml/mainMenu.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller));
        MainApp.changeScene(controller, loader);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        p1Name.setText(sideP1.getPlayer().getName());
        p2Name.setText(sideP2.getPlayer().getName());

        p1SlotsCh.add(p1Ch1);
        p1SlotsCh.add(p1Ch2);
        p1SlotsCh.add(p1Ch3);
        p1SlotsCh.add(p1Ch4);
        p1SlotsCh.add(p1Ch5);
        p1SlotsCh.add(p1Ch6);
        p1SlotsCh.add(p1Ch7);

        p1TilesPr.add(p1TilePr1);
        p1TilesPr.add(p1TilePr2);
        p1TilesPr.add(p1TilePr3);
        p1TilesPr.add(p1TilePr4);
        p1TilesPr.add(p1TilePr5);
        p1TilesPr.add(p1TilePr6);
        p1TilesPr.add(p1TilePr7);

        p1TilesSr.add(p1TileSr1);
        p1TilesSr.add(p1TileSr2);

        // refresh les slots du challenge (image de fond)
        refreshSlots(sideP1, p1SlotsCh);
        refreshSlots(sideP2, p2SlotsCh);

        // refresh le contenus des Tiles GUI et on les attaches au slots
        refreshTiles(sideP1.getPlayerRack().getRack(), p1SlotsPr, p1TilesPr);
        refreshTiles(sideP1.getSwapRack().getRack(),p1SlotsSr, p1TilesSr);
        refreshTiles(sideP2.getPlayerRack().getRack(),p2SlotsPr, p2TilesPr);
        refreshTiles(sideP2.getSwapRack().getRack(), p2SlotsSr, p2TilesSr);
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
            // Set les labels du composant tile 0 = score, 1 = value
//            tiles.get(i).getChildren().get(0).setText(tile.getScore());
//            tiles.get(i).getChildren().get(1).setText(tile.getValue());
              addTileToSlot(slots.get(i), tiles.get(i));
        }
    }

    // Ajoute la tile dans un slot
    @FXML
    private void addTileToSlot(AnchorPane slot, AnchorPane tile) {
        slot.getChildren().add(tile);
    }

    // Récupère la tile d'un slot
    @FXML
    private Node getTile(AnchorPane slot) {
        return slot.getChildren().remove(0);
    }

    @FXML
    private void moveTile(AnchorPane slotOrigin, AnchorPane slotDest){
        if(slotDest.getChildren().isEmpty()) {
            slotDest.getChildren().add(slotOrigin.getChildren().remove(0));
        }
    }

}

