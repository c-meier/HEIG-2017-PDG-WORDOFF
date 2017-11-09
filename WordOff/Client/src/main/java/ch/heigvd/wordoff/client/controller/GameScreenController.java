package ch.heigvd.wordoff.client.controller;


import ch.heigvd.wordoff.client.Dialog;
import ch.heigvd.wordoff.client.MainApp;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import ch.heigvd.wordoff.client.logic.Game;
import ch.heigvd.wordoff.common.Dto.SideDto;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Dto.Slots.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML
    private Button shuffleButton;
    @FXML
    private Button discardButton;

    private int numberTilesOnChallengeRack = 0;
    // Listes Player 1
    private List<AnchorPane> p1TilesPr = new ArrayList<>();
    private List<AnchorPane> p1TilesSr = new ArrayList<>();
    private List<StackPane> p1SlotsCh = new ArrayList<>();
    private List<StackPane> p1SlotsSr = new ArrayList<>();
    private List<StackPane> p1SlotsPr = new ArrayList<>();
    // Listes Player 2
    private List<AnchorPane> p2TilesPr = new ArrayList<>();
    private List<AnchorPane> p2TilesSr = new ArrayList<>();
    private List<StackPane> p2SlotsCh = new ArrayList<>();
    private List<StackPane> p2SlotsSr = new ArrayList<>();
    private List<StackPane> p2SlotsPr = new ArrayList<>();



    // PLAYER 1
    // ChallengeDto background and foreground
    @FXML
    private AnchorPane p1Ch1Back, p1Ch2Back, p1Ch3Back, p1Ch4Back, p1Ch5Back, p1Ch6Back, p1Ch7Back;
    @FXML
    private StackPane p1Ch1Fore, p1Ch2Fore, p1Ch3Fore, p1Ch4Fore, p1Ch5Fore, p1Ch6Fore, p1Ch7Fore;
    // Player Rack
    @FXML
    private StackPane p1Pr1Fore, p1Pr2Fore, p1Pr3Fore, p1Pr4Fore, p1Pr5Fore, p1Pr6Fore, p1Pr7Fore;
    // SwapSlotDto Rack
    @FXML
    private StackPane p1Sr1Fore, p1Sr2Fore;
    // Tiles
    @FXML
    private AnchorPane p1TilePr1, p1TilePr2, p1TilePr3, p1TilePr4, p1TilePr5, p1TilePr6, p1TilePr7;
    @FXML
    private AnchorPane p1TileSr1, p1TileSr2;

    // PLAYER 2
    // Challenge background and foreground
    @FXML
    private AnchorPane p2Ch1Back, p2Ch2Back, p2Ch3Back, p2Ch4Back, p2Ch5Back, p2Ch6Back, p2Ch7Back;
    @FXML
    private StackPane p2Ch1Fore, p2Ch2Fore, p2Ch3Fore, p2Ch4Fore, p2Ch5Fore, p2Ch6Fore, p2Ch7Fore;
    // Player rack
    @FXML
    private StackPane p2Pr1Fore, p2Pr2Fore, p2Pr3Fore, p2Pr4Fore, p2Pr5Fore, p2Pr6Fore, p2Pr7Fore;
    // SwapSlotDto rack
    private StackPane p2Sr1Fore, p2Sr2Fore;
    @FXML
    private AnchorPane p2TileSr1, p2TileSr2;
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


    @FXML
    private void shuffleClear(){
        if(shuffleButton.getText().compareTo("Melanger") == 0){
            shuffle();
        }else{
            clear();
        }
    }

    private void shuffle(){
        List<ITile> tiles = game.getSideP1().getPlayerRack().getTiles();
        Collections.shuffle(tiles);
        setTiles(tiles, p1TilesPr, false);
    }

    private void clear(){
        for(StackPane slotParent : p1SlotsCh){
            if(!slotParent.getChildren().isEmpty()){
                AnchorPane tile = (AnchorPane) slotParent.getChildren().get(0);
                move(tile, slotParent);
            }
        }
        shuffleButton.setText("Melanger");
        numberTilesOnChallengeRack = 0;
    }

    @FXML
    private void discardOrPasse(){
        if(discardButton.getText().equals("Jeter")){
            discard();
        }
        else{
            // appel passer
        }
    }

    private void discard(){
        // TODO ouvrir l'alert choix
        System.out.println("Click discard");
    }

    private void passed(){
        // TODO passer le tour
        System.out.println("Click passed");
    }

    @FXML
    private void peek(){
        // TODO test confirmation du hint côté serveur
      //  if(   ){
            setTiles(game.getSideP2().getPlayerRack().getTiles(), p2TilesPr, false);
            setVisible(p2TilesPr,true);
     //   }
    }

    @FXML
    private void hint(){
        // TODO
        System.out.println("Click hint");
    }

    @FXML
    private void play(){
        // TODO envoyer le mot
        // TODO vérifie si il y a un message d'erreur du serveur
        System.out.println("Click play");
        boolean error = true;
        String errorString = "Error 404 - .......";
        if(error == false){
            // TODO set les side du game
            setVisible(p2TilesPr,false);
            setStateGame();
        }else{
            // TODO afficher un message d'erreur
            Dialog.getInstance().signalError(errorString);
        }
    }


    /**
     * Recoit le side lié à la game
     *
     * @param game
     */
    private void setState(Game game) {
        this.game = game;
        p1Name.setText("Player One");
        p2Name.setText("Player Two");
        /*
        p1Name.setText(sideP1.getPlayer().getName());
        p2Name.setText(sideP2.getPlayer().getName());
        */

        // Initialization lists
        initList();
        // Maj de l'état du jeu
        setStateGame();
    }

    private void setStateGame(){
        // Set les challenge slots
        setBackgroundChallenge(game.getSideP1(), p1Ch1Back, p1Ch2Back, p1Ch3Back, p1Ch4Back, p1Ch5Back, p1Ch6Back, p1Ch7Back);
        setBackgroundChallenge(game.getSideP2(), p2Ch1Back, p2Ch2Back, p2Ch3Back, p2Ch4Back, p2Ch5Back, p2Ch6Back, p2Ch7Back);
        // Set les swaps racks
        setTiles(game.getSideP1().getChallenge().getSwapRack().getTiles(), p1TilesSr, true);
        setTiles(game.getSideP2().getChallenge().getSwapRack().getTiles(), p2TilesSr, false);
        // Set le playerRack
        setTiles(game.getSideP1().getPlayerRack().getTiles(), p1TilesPr, true);
    }

    /**
     * Refresh GUI slots according to state of challenge
     *
     * @param side  player side
     * @param slots GUI challenge of player
     */
    private void setBackgroundChallenge(SideDto side, AnchorPane... slots) {
        // Placer les images du challenge selon les slots
        int i = 0;
        String styleClass="";
        for (ISlot slot : side.getChallenge().getSlots()) {
            // Get image source
            if ( slot.getClass() == LastSlotDto.class) {
                styleClass = "slot_7letter";
            } else if (slot.getClass() == L2SlotDto.class) {
                styleClass = "slot_2L";
            } else if (slot.getClass() == L3SlotDto.class) {
                styleClass = "slot_3L";
            } else if (slot.getClass() == SwapSlotDto.class) {
                styleClass = "slot_W";
            } else {
                styleClass = "slot";
            }

            setBackground(slots[i++], styleClass);
        }
    }

    private void setBackground(AnchorPane slot, String styleClass){
        slot.getStyleClass().clear();
        slot.getStyleClass().add(styleClass);
    }

    /**
     * Refresh GUI tiles according to state of rack
     *
     * @param rack  Game rack
     * @param tiles GUI tiles
     */
    private void setTiles(List<ITile> rack, List<AnchorPane> tiles, boolean withListener) {
        int i = 0;
        for (ITile tile : rack) {
            // Maj tile GUI
            Label value = (Label) tiles.get(i).getChildren().get(0);
            Label score = (Label) tiles.get(i).getChildren().get(1);
            Label id = (Label) tiles.get(i).getChildren().get(2);
            value.setText(String.valueOf(tile.getValue()).toUpperCase());
            score.setText(String.valueOf(tile.getScore()));
            id.setText(String.valueOf(tile.getId()));

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
        if (rack.size() < tiles.size()) {
            for (int j = rack.size(); j < tiles.size(); j++) {
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
    private void addTileToSlot(StackPane slot, AnchorPane tile) {
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
    private StackPane firstSlotEmpty(List<StackPane> slots) {
        for (StackPane p : slots) {
            if (p.getChildren().isEmpty()) {
                return p;
            }
        }
        return null;
    }

    private void moveOnDragAndDrop(DragEvent event) {
        // TODO
    }

    /**
     * Move a tile to challenge from swapRack or playerRack, or inverse, when tile is clicked.
     * @param event mouseEvent
     */
    private void moveOnClick(MouseEvent event) {
        AnchorPane tileSelect = (AnchorPane) event.getSource();
        StackPane slotParent = (StackPane) tileSelect.getParent();
        // Apply move
        move(tileSelect,slotParent);
        if(numberTilesOnChallengeRack == 0){
            shuffleButton.setText("Melanger");
        }else{
            shuffleButton.setText("Effacer");
        }
        // TODO editer l'tat du wordAlyzer
    }

    /**
     *
     * @param tileSelect
     * @param slotParent
     */
    private void move(AnchorPane tileSelect, StackPane slotParent){
        int idTile = Integer.valueOf(((Label)tileSelect.getChildren().get(2)).getText());
        StackPane destination = null;

        if (p1SlotsPr.contains(slotParent)) {
            // move to challenge from player rack
            destination = firstSlotEmpty(p1SlotsCh);
            if (null != destination) {
                addTileToSlot(destination, tileSelect);
                numberTilesOnChallengeRack++;

                // Move tile in logic game
                ITile tile = game.getSideP1().getPlayerRack().removeTile(idTile);
                game.getSideP1().getChallenge().addTile(tile);
            }
        } else if (p1SlotsSr.contains(slotParent)) {
            // Move to challenge from swap rack
            destination = firstSlotEmpty(p1SlotsCh);
            if (null != destination) {
                addTileToSlot(destination, tileSelect);
                numberTilesOnChallengeRack++;

                // Move tile in logic game
                ITile tile = game.getSideP1().getChallenge().getSwapRack().removeTile(idTile);
                game.getSideP1().getChallenge().addTile(tile);
            }
        } else {
            // Move to swapRack from challenge
            if (p1TilesSr.contains(tileSelect)) {
                // Maj GUI
                destination = firstSlotEmpty(p1SlotsSr);
                addTileToSlot(destination, tileSelect);
                numberTilesOnChallengeRack--;

                // Maj Logic
                ITile tile = game.getSideP1().getChallenge().getTileById(idTile);
                game.getSideP1().getChallenge().getSwapRack().addTile(tile);
            } else {
                // Move to player rack from challenge

                // Maj GUI
                destination = firstSlotEmpty(p1SlotsPr);
                addTileToSlot(destination, tileSelect);
                numberTilesOnChallengeRack--;

                // Maj logic
                ITile tile = game.getSideP1().getChallenge().getTileById(idTile);
                game.getSideP1().getPlayerRack().addTile(tile);
            }
        }

    }



    /**
     * Initialization of list
     */
    private void initList() {
        // initialization player 1
        // Challenge
        addConentListStackPane(p1SlotsCh, p1Ch1Fore, p1Ch2Fore, p1Ch3Fore, p1Ch4Fore, p1Ch5Fore, p1Ch6Fore, p1Ch7Fore);
        // Player rack
        addConentListStackPane(p1SlotsPr, p1Pr1Fore, p1Pr2Fore, p1Pr3Fore, p1Pr4Fore, p1Pr5Fore, p1Pr6Fore, p1Pr7Fore);
        // Tiles player rack
        addConentListAnchorePane(p1TilesPr, true, p1TilePr1, p1TilePr2, p1TilePr3, p1TilePr4, p1TilePr5, p1TilePr6, p1TilePr7);
        // SwapSlotDto rack
        addConentListStackPane(p1SlotsSr, p1Sr1Fore, p1Sr2Fore);
        // Tiles swap rack
        addConentListAnchorePane(p1TilesSr, true, p1TileSr1, p1TileSr2);

        // initialization player 2
        // Challenge
        addConentListStackPane(p2SlotsCh, p2Ch1Fore, p2Ch2Fore, p2Ch3Fore, p2Ch4Fore, p2Ch5Fore, p2Ch6Fore, p2Ch7Fore);
        // Player rack
        addConentListStackPane(p2SlotsPr, p2Pr1Fore, p2Pr2Fore, p2Pr3Fore, p2Pr4Fore, p2Pr5Fore, p2Pr6Fore, p2Pr7Fore);
        // Tiles player rack
        addConentListAnchorePane(p2TilesPr, false, p2TilePr1, p2TilePr2, p2TilePr3, p2TilePr4, p2TilePr5, p2TilePr6, p2TilePr7);
        // SwapSlotDto rack
        addConentListStackPane(p2SlotsSr, p2Sr1Fore, p2Sr2Fore);
        // Tiles swap rack
        addConentListAnchorePane(p2TilesSr, true, p2TileSr1, p2TileSr2);

    }

    /**
     * Initialized content of list
     *
     * @param list    list to initialize
     * @param content content of list
     */
    private void addConentListAnchorePane(List<AnchorPane> list, boolean isVisible, AnchorPane... content) {
        for (AnchorPane p : content) {
            list.add(p);
            p.setVisible(isVisible);
        }
    }

    private void setVisible(List<AnchorPane> tiles, boolean isVisible){
        for (AnchorPane p : tiles) {
            p.setVisible(isVisible);
        }
    }

    private void addConentListStackPane(List<StackPane> list, StackPane... content) {
        for (StackPane p : content) {
            list.add(p);
        }
    }

}

