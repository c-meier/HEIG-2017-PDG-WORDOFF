package ch.heigvd.wordoff.client.controller;


import ch.heigvd.wordoff.client.MainApp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ch.heigvd.wordoff.client.Model.ISlot;
import ch.heigvd.wordoff.client.logic.Game;
import ch.heigvd.wordoff.client.logic.Side;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

/**
 * @author Gabriel Luthier
 */
public class GameScreenController implements Initializable {

    private Game game;
    @FXML
    private Label p1Name, p2Name;
    // emplacement image

    // Listes Player 1
    private List<AnchorPane> p1TilesPr = new ArrayList<>();
    private List<AnchorPane> p1TilesSr = new ArrayList<>();
    private List<AnchorPane> p1SlotsCh = new ArrayList<>();
    private List<AnchorPane> p1SlotsSr = new ArrayList<>();
    private List<AnchorPane> p1SlotsPr = new ArrayList<>();

    // Listes Player 2
    private List<AnchorPane> p2SlotsCh = new ArrayList<>();
    private List<AnchorPane> p2TilesPr = new ArrayList<>();
    private List<AnchorPane> p2TilesSr = new ArrayList<>();
    private List<AnchorPane> p2SlotsSr = new ArrayList<>();
    private List<AnchorPane> p2SlotsPr = new ArrayList<>();

    // PLAYER 1
    // Challenge background and foreground
    @FXML
    private AnchorPane p1Ch1Back, p1Ch2Back, p1Ch3Back, p1Ch4Back, p1Ch5Back, p1Ch6Back, p1Ch7Back;
    @FXML
    private AnchorPane p1Ch1Fore, p1Ch2Fore, p1Ch3Fore, p1Ch4Fore, p1Ch5Fore, p1Ch6Fore, p1Ch7Fore;
    // Player Rack
    @FXML
    private AnchorPane p1Pr1Fore, p1Pr2Fore, p1Pr3Fore, p1Pr4Fore, p1Pr5Fore, p1Pr6Fore, p1Pr7Fore;
    // Swap Rack
    @FXML
    private AnchorPane p1Sr1Fore, p1Sr2Fore;
    // Tiles
    @FXML
    private AnchorPane p1TilePr1, p1TilePr2, p1TilePr3, p1TilePr4, p1TilePr5, p1TilePr6, p1TilePr7;
    //  @FXML
    //   private AnchorPane p1TileSr1, p1TileSr2;

    // PLAYER 2
    // Challenge background and foreground
    @FXML
    AnchorPane p2Ch1Back, p2Ch2Back, p2Ch3Back, p2Ch4Back, p2Ch5Back, p2Ch6Back, p2Ch7Back;
    @FXML
    AnchorPane p2Ch1Fore, p2Ch2Fore, p2Ch3Fore, p2Ch4Fore, p2Ch5Fore, p2Ch6Fore, p2Ch7Fore;
    // Player rack
    @FXML
    AnchorPane p2Pr1Fore, p2Pr2Fore, p2Pr3Fore, p2Pr4Fore, p2Pr5Fore, p2Pr6Fore, p2Pr7Fore;
    // Swap rack
    AnchorPane p2Sr1Fore, p2Sr2Fore;
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

        p1Name.setText("Player One");
        p2Name.setText("Player Two");
        /*
        p1Name.setText(sideP1.getPlayer().getName());
        p2Name.setText(sideP2.getPlayer().getName());
        */

        // Initialization lists
        initList();

        // Initialization of challenge background
        setBackgroundChallenge(sideP1, p1Ch1Back, p1Ch2Back, p1Ch3Back, p1Ch4Back, p1Ch5Back, p1Ch6Back, p1Ch7Back);
        setBackgroundChallenge(sideP2, p2Ch1Back, p2Ch2Back, p2Ch3Back, p2Ch4Back, p2Ch5Back, p2Ch6Back, p2Ch7Back);

        // refresh le contenus des Tiles GUI et on les attaches au slots
        setTiles(sideP1.getPlayerRack().getRack(), p1TilesPr, true);
        //  refreshTiles(sideP1.getSwapRack().getRack(), p1SlotsSr, p1TilesSr);

        setTiles(sideP2.getPlayerRack().getRack(), p2TilesPr, false);
        //refreshTiles(sideP2.getSwapRack().getRack(), p2SlotsSr, p2TilesSr);

    }

    /**
     * Refresh GUI slots according to state of challenge
     *
     * @param side  player side
     * @param slots GUI challenge of player
     */
    @FXML
    private void setBackgroundChallenge(Side side, AnchorPane... slots) {
        // Placer les images du challenge selon les slots
        int i = 0;
        for (ISlot slot : side.getChallenge().getSlots()) {
            slots[i++].setBackground(new Background(new BackgroundImage(slot.getImage(),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null)));
        }
    }

    /**
     * Refresh GUI tiles according to state of rack
     *
     * @param rack  Game rack
     * @param tiles GUI tiles
     */
    @FXML
    private void setTiles(List<Tile> rack, List<AnchorPane> tiles, boolean withListener) {
        int i = 0;
        for (Tile tile : rack) {
            // Maj tile GUI
            Label value = (Label) tiles.get(i).getChildren().get(0);
            Label score = (Label) tiles.get(i).getChildren().get(1);
            value.setText(String.valueOf(tile.getValue()).toUpperCase());
            score.setText(String.valueOf(tile.getScore()));

            if (withListener) {
                // Add listener mouseClicket
                tiles.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        moveOnClick(event);
                    }
                });
                // Add listener drag nd drop
                tiles.get(i).setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        moveOnDragAndDrop(event);
                    }
                });
            }
            i++;
        }
        // Hide tiles not used
        if(rack.size() < tiles.size()){
            for(int j = rack.size() ; j < tiles.size(); j++){
                tiles.get(i).setVisible(false);
            }
        }
    }

    /**
     * Add tile to slot
     *
     * @param slot destination slot
     * @param tile tile to add
     */
    private void addTileToSlot(AnchorPane slot, AnchorPane tile) {
        if (slot != null && slot.getChildren().isEmpty()) {
            slot.getChildren().add(tile);
        }
    }

    /**
     * Return first slot available on rack, or challenge.
     *
     * @param slots rack or challenge
     * @return first slot available
     */
    private AnchorPane firstSlotEmpty(List<AnchorPane> slots) {
        for (AnchorPane p : slots) {
            if (p.getChildren().isEmpty()) {
                return p;
            }
        }
        return null;
    }

    private void moveOnDragAndDrop(DragEvent event) {
        // TODO
    }

    private void moveOnClick(MouseEvent event) {
        AnchorPane tileSelect = (AnchorPane) event.getSource();
        AnchorPane slotParent = (AnchorPane) tileSelect.getParent();

        if (p1SlotsPr.contains(slotParent)) {
            // move to challenge from player rack
            addTileToSlot(firstSlotEmpty(p1SlotsCh), tileSelect);
            //  TODO Mettre à jour les racks
           /* int position = p1SlotsPr.indexOf(slotParent);
            Tile tile = game.getSideP1().getPlayerRack().getRack().get(position);
            game.getSideP1().getPlayerRack().getTile(tile.getId());
            game.getSideP1().getChallenge().addTile(tile);*/

        } else if (p1SlotsSr.contains(slotParent)) {
            // Move to challenge from swap rack
            addTileToSlot(firstSlotEmpty(p1SlotsCh), tileSelect);
            //  TODO Mettre à jour les racks
        } else {
            // Move to swapRack from challenge
            if (p1TilesSr.contains(tileSelect)) {
                addTileToSlot(firstSlotEmpty(p1SlotsSr), tileSelect);
                //  TODO Mettre à jour les racks
            } else {
                // Move to player rack from challenge
                //  TODO Mettre à jour les racks
                addTileToSlot(firstSlotEmpty(p1SlotsPr), tileSelect);
            }
        }

    }


    /**
     * Initialization of list
     */
    private void initList() {
        // initialization player 1
        // Challenge
        addConentList(p1SlotsCh, p1Ch1Fore, p1Ch2Fore, p1Ch3Fore, p1Ch4Fore, p1Ch5Fore, p1Ch6Fore, p1Ch7Fore);
        // Player rack
        addConentList(p1SlotsPr, p1Pr1Fore, p1Pr2Fore, p1Pr3Fore, p1Pr4Fore, p1Pr5Fore, p1Pr6Fore, p1Pr7Fore);
        // Tiles player rack
        addConentList(p1TilesPr, p1TilePr1, p1TilePr2, p1TilePr3, p1TilePr4, p1TilePr5, p1TilePr6, p1TilePr7);
        // Swap rack
        addConentList(p1SlotsSr, p1Sr1Fore, p1Sr2Fore);
        // TODO Tile swap rack
        // addConentList(p1TilesSr, p1TilesSr1, p1TilesSr2);

        // initialization player 2
        // Challenge
        addConentList(p2SlotsCh, p2Ch1Fore, p2Ch2Fore, p2Ch3Fore, p2Ch4Fore, p2Ch5Fore, p2Ch6Fore, p2Ch7Fore);
        // Player rack
        addConentList(p2SlotsPr, p2Pr1Fore, p2Pr2Fore, p2Pr3Fore, p2Pr4Fore, p2Pr5Fore, p2Pr6Fore, p2Pr7Fore);
        // Tiles player rack
        addConentList(p2TilesPr, p2TilePr1, p2TilePr2, p2TilePr3, p2TilePr4, p2TilePr5, p2TilePr6, p2TilePr7);
        // Swap rack
        addConentList(p2SlotsSr, p2Sr1Fore, p2Sr2Fore);
        // TODO Tile swap rack
        // addConentList(p2TilesSr, p2TilesSr1, p2TilesSr2);

    }

    /**
     * Initialized content of list
     *
     * @param list    list to initialize
     * @param content content of list
     */
    private void addConentList(List<AnchorPane> list, AnchorPane... content) {
        for (AnchorPane p : content) {
            list.add(p);
        }
    }

}

