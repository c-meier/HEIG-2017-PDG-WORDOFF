package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.GameApi;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Exception.UnprocessableEntityException;
import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.client.Util.UtilChangeScene;
import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dictionary;
import ch.heigvd.wordoff.common.DictionaryLoader;
import ch.heigvd.wordoff.common.Dto.Game.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.L2SlotDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.L3SlotDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.LastSlotDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.SwapSlotDto;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gabriel Luthier
 */
public class GameScreenController implements Initializable {

    private GameDto game;
    private List<Character> alphabet;
    @FXML
    private Label p1Name, p2Name;
    @FXML
    private Button shuffleButton;
    @FXML
    private Button discardButton;
    @FXML
    private Button playButton;
    @FXML
    private Label tilesRemaining;
    @FXML
    private ImageView flag;

    // Composant du WordAlyzer
    @FXML
    private Label scoreWordAlyzer;
    @FXML
    private CheckBox checkWordAlyzer;
    @FXML
    private Circle circle1WordAlyzer, circle2WordAlyzer, circle3WordAlyzer, circle4WordAlyzer, circle5WordAlyzer;

    private int numberTilesOnChallengeRack = 0;
    // Listes Player 1
    private List<AnchorPane> p1TilesPr = new ArrayList<>();
    private List<AnchorPane> p1TilesSr = new ArrayList<>();
    private List<StackPane> p1SlotsCh = new ArrayList<>();
    private List<StackPane> p1SlotsSr = new ArrayList<>();
    private List<StackPane> p1SlotsPr = new ArrayList<>();
    private List<StackPane> p1OriginPr = new ArrayList<>();
    private List<StackPane> p1OriginSr = new ArrayList<>();
    // Listes Player 2
    private List<AnchorPane> p2TilesPr = new ArrayList<>();
    private List<AnchorPane> p2TilesSr = new ArrayList<>();
    private List<AnchorPane> p2TilesCh = new ArrayList<>();
    private List<StackPane> p2SlotsCh = new ArrayList<>();
    private List<StackPane> p2SlotsSr = new ArrayList<>();
    private List<StackPane> p2SlotsPr = new ArrayList<>();

    private Dictionary dico;

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
    private AnchorPane p2TileCh1, p2TileCh2, p2TileCh3, p2TileCh4, p2TileCh5, p2TileCh6, p2TileCh7;

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        UtilChangeScene.getInstance().handleGotoMenu();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void displayState() {
        System.out.println("Size pr : " + game.getMySide().getPlayerRack().getTiles().size());
        System.out.print("Player Rack : ");
        for (ITile tile : game.getMySide().getPlayerRack().getTiles()) {
            System.out.print(tile.getValue() + " ");
        }
        System.out.print("\nChallenge : ");
        for (ISlot tile : game.getMySide().getChallenge().getSlots()) {
            if (!tile.isEmpty())
                System.out.print(tile.getTile().getValue() + " ");
        }
        System.out.print("\nSwap Rack : ");
        for (ITile tile : game.getMySide().getChallenge().getSwapRack().getTiles()) {
            System.out.print(tile.getValue() + " ");
        }
    }

    protected void setGame(GameDto game) {
        this.game = game;
        setNumberOfTiles();
        setLang();
        setState(this.game);
        DictionaryLoader dicoLoad = new DictionaryLoader();
        this.dico = dicoLoad.getDico(this.game.getLang());

    }

    private void setLang() {
        this.flag.setImage(new Image(getClass().getResource("/images/" + game.getLang() + ".png").toExternalForm()));
    }

    protected void setAlphabet(List<Character> alphabet){
        this.alphabet = alphabet;
    }

    private void setNumberOfTiles() {
        int size = game.getBagSize();
        String text = "";
        if (size > 0) {
            text = size + " tuiles restantes";
        } else {
            text = size + " tuile restante";
        }
        this.tilesRemaining.setText(text);
    }

    @FXML
    private void shuffleClear() {
        if (shuffleButton.getText().compareTo("Melanger") == 0) {
            shuffle();
        } else {
            clear(p1SlotsCh);
        }
    }

    private void shuffle() {
        List<ITile> tiles = this.game.getMySide().getPlayerRack().getTiles();
        Collections.shuffle(tiles);
        setTiles(tiles, p1TilesPr, false);
    }

    private void clear(List<StackPane> slotsChallenge) {
        for (StackPane slotParent : slotsChallenge) {
            if (!slotParent.getChildren().isEmpty()) {
                AnchorPane tile = (AnchorPane) slotParent.getChildren().get(0);
                move(tile, slotParent);
            }
        }
        shuffleButton.setText("Melanger");
        numberTilesOnChallengeRack = 0;
    }

    @FXML
    private void discardOrPasse() {
        if (discardButton.getText().equals("Jeter")) {
            discard();
        } else {
            // TODO demande au serveur pour passer
        }
    }

    private void discard() {
        // TODO appeler discard au serveur
        System.out.println("Click discard");
    }

    private void passed() {
        // TODO appeler passer le tour au serveur
        System.out.println("Click passed");
    }

    @FXML
    private void peek() {
        // TODO test confirmation du hint côté serveur et réception du rack à afficher
        /*
        if(   ){
            setTiles(game.getOtherSide().getPlayerRack().getTiles(), p2TilesPr, false);
            setVisible(p2TilesPr,true);
        }
     */
    }

    @FXML
    private void refresh(){
        try {
            this.game = GameApi.getGame(game.getId());
            // Cache les cases du player 2 (cas du pouvoir apercu activé pendant le tour
            setVisible(p2TilesPr, false);
            // Clear les valeurs des tiles GUI
            clearTiles(p1TilesPr);
            clearTiles(p1TilesSr);
            clearTiles(p2TilesSr);
            // Replace les tiles aux slots d'origines
            replaceTilesOrigin(p1SlotsCh);
            // Actualise l'état du jeu
            setStateGame();
            setNumberOfTiles();
            majWordAlyzer();
        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalError("Une erreur s'est produite. Veuillez vous reconnecter");
        }
    }

    @FXML
    private void hint() {
        // TODO etat temporaire
        System.out.println("Click hint");
    }

    @FXML
    private void playOrVerif() {
        if (playButton.getText().equals("Jouer")) {
            play();
        } else {
            verif();
        }
    }

    private boolean challengeIsValid(List<ISlot> challenge) {
        // Première case vide
        if (challenge.get(0).isEmpty())
            return false;

        // Aucune tile sur le challenge
        if (numberTilesOnChallengeRack == 0) {
            return false;
        }

        // Trous dans le mot
        for (int i = 0; i < challenge.size(); i++) {
            if (challenge.get(i).isEmpty() && (i != challenge.size() - 1)) {
                // Vérifier si une lettre suit
                for (int j = i + 1; j < challenge.size(); j++) {
                    if (!challenge.get(j).isEmpty()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean majWordAlyzer() {
        String word = "";
        int score = 0;
        boolean isValidWord = false;
        ChallengeDto challenge = game.getMySide().getChallenge();
        if (numberTilesOnChallengeRack > 0) {
            for (ISlot slot : game.getMySide().getChallenge().getSlots()) {
                if (!slot.isEmpty()) {
                    word += slot.getTile().getValue();
                    score += slot.getTile().getScore();
                }
            }
            if (challengeIsValid(challenge.getSlots()))
                isValidWord = dico.contains(word);
        }
        scoreWordAlyzer.setText(String.valueOf(score));
        checkWordAlyzer.setSelected(isValidWord);
        return isValidWord;
    }

    private void verif() {
        boolean isValidWord = majWordAlyzer();
        String text = isValidWord ? "Ce mot est correct" : "Ce mot n'existe pas";
        Dialog.getInstance().signalInformation(text);
    }

    private void play() {
        if (majWordAlyzer() == true) {
            try {
                this.game = GameApi.play(game.getId(), game.getMySide().getChallenge());
                // Cache les cases du player 2 (cas du pouvoir apercu activé pendant le tour
                setVisible(p2TilesPr, false);
                // Clear les valeurs des tiles GUI
                clearTiles(p1TilesPr);
                clearTiles(p1TilesSr);
                clearTiles(p2TilesSr);
                // Replace les tiles aux slots d'origines
                replaceTilesOrigin(p1SlotsCh);
                // Actualise l'état du jeu
                setStateGame();
                setNumberOfTiles();
                majWordAlyzer();
            } catch (TokenNotFoundException e) {
                Dialog.getInstance().signalError("Une erreur s'est produite. Veuillez vous reconnecter");
            } catch (UnprocessableEntityException e) {
                Dialog.getInstance().signalInformation("Ce n'est pas votre tour de jouer");
            }
        } else {
            Dialog.getInstance().signalInformation("Ce mot n'est pas valide");
        }
    }

    private void replaceTilesOrigin(List<StackPane> slotsChallenge) {
        if (slotsChallenge.equals(p1SlotsCh)) {
            for (StackPane slot : p1SlotsCh) {
                if (!slot.getChildren().isEmpty()) {
                    AnchorPane tile = (AnchorPane) slot.getChildren().get(0);
                    // check origin
                    if (p1TilesPr.contains(tile)) {
                        addTileToSlot(firstSlotEmpty(p1SlotsPr), tile);
                    } else {
                        addTileToSlot(firstSlotEmpty(p1SlotsSr), tile);
                    }
                }
            }
        } else {
            for (StackPane slot : p2SlotsCh) {
                if (!slot.getChildren().isEmpty()) {
                    AnchorPane tile = (AnchorPane) slot.getChildren().get(0);
                    // check origin
                    if (p2TilesPr.contains(tile)) {
                        addTileToSlot(firstSlotEmpty(p2SlotsPr), tile);
                    } else {
                        addTileToSlot(firstSlotEmpty(p2SlotsSr), tile);
                    }
                }
            }
        }
    }


    private void clearTiles(List<AnchorPane> tiles) {
        int i = 0;
        for (AnchorPane tile : tiles) {
            Label value = (Label) tiles.get(i).getChildren().get(0);
            Label score = (Label) tiles.get(i).getChildren().get(1);
            Label id = (Label) tiles.get(i).getChildren().get(2);
            value.setText("");
            score.setText("");
            id.setText("");
            i++;
        }
    }

    /**
     * Recoit le side lié à la game
     *
     * @param game
     */
    private void setState(GameDto game) {
        p1Name.setText(game.getMySide().getPlayer().getName());
        p2Name.setText(game.getOtherSide().getPlayer().getName());

        // Initialization lists
        initList();
        // Maj de l'état du jeu
        setStateGame();
    }

    private void setStateChallengeOtherSide() {
        if (game.getOtherSide().getChallenge().getSlots().isEmpty()) {
            for (AnchorPane pane : p2TilesCh) {
                pane.setVisible(false);
            }
        } else {
            int i = 0;
            for (ISlot slot : game.getOtherSide().getChallenge().getSlots()) {
                if (slot.isEmpty()) {
                    p2TilesCh.get(i).setVisible(false);
                } else {
                    setTile(p2TilesCh.get(i), slot.getTile());
                    p2TilesCh.get(i).setVisible(true);
                }
                i++;
            }
        }
    }

    private void setStateGame() {
        // Set les challenge slots
        setBackgroundChallenge(this.game.getMySide().getChallenge(), p1Ch1Back, p1Ch2Back, p1Ch3Back, p1Ch4Back, p1Ch5Back, p1Ch6Back, p1Ch7Back);
        setBackgroundChallenge(this.game.getOtherSide().getChallenge(), p2Ch1Back, p2Ch2Back, p2Ch3Back, p2Ch4Back, p2Ch5Back, p2Ch6Back, p2Ch7Back);
        setStateChallengeOtherSide();
        // Set les swaps racks
        setTiles(this.game.getMySide().getChallenge().getSwapRack().getTiles(), p1TilesSr, true);
        setTiles(this.game.getOtherSide().getChallenge().getSwapRack().getTiles(), p2TilesSr, false);
        // Set le playerRack
        setTiles(this.game.getMySide().getPlayerRack().getTiles(), p1TilesPr, true);

        // Maj de myTurn
        if (this.game.isMyTurn() == false) {
            playButton.setText("Vérifier");
        } else {
            playButton.setText("Jouer");
        }
    }

    /**
     * Refresh GUI slots according to state of challenge
     *
     * @param challenge player challenge
     * @param slots     GUI challenge of player
     */
    private void setBackgroundChallenge(ChallengeDto challenge, AnchorPane... slots) {
        // Placer les images du challenge selon les slots
        int i = 0;
        String styleClass = "";
        for (ISlot slot : challenge.getSlots()) {
            // Get image source
            if (slot.getClass() == LastSlotDto.class) {
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

    private void setBackground(AnchorPane slot, String styleClass) {
        slot.getStyleClass().clear();
        slot.getStyleClass().add(styleClass);
    }

    /**
     * Change state of tile GUI from tile logic
     *
     * @param tilePane
     * @param tileLogic
     */
    private void setTile(AnchorPane tilePane, ITile tileLogic) {
        Label value = (Label) tilePane.getChildren().get(0);
        Label score = (Label) tilePane.getChildren().get(1);
        Label id = (Label) tilePane.getChildren().get(2);
        if (tileLogic.getScore() == 0) {
            value.setText("");
        } else {
            value.setText(String.valueOf(tileLogic.getValue()).toUpperCase());
        }
        score.setText(String.valueOf(tileLogic.getScore()));
        id.setText(String.valueOf(tileLogic.getId()));
        tilePane.setVisible(true);
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
            setTile(tiles.get(i), tile);
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
                tiles.get(j).setVisible(false);
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
        // TODO drag and drop
    }

    /**
     * Move a tile to challenge from swapRack or playerRack, or inverse, when tile is clicked.
     *
     * @param event mouseEvent
     */
    private void moveOnClick(MouseEvent event) {
        AnchorPane tileSelect = (AnchorPane) event.getSource();
        StackPane slotParent = (StackPane) tileSelect.getParent();

        Label tileValue = ((Label)tileSelect.getChildren().get(0));
        Label tileScore = ((Label)tileSelect.getChildren().get(1));
        int tileId = Integer.valueOf(((Label) tileSelect.getChildren().get(2)).getText());
        boolean cancelMove = false;

        // TODO Modifier la logique. On test d'abbord si la tile est un jocker et seulement après son origine.
        //If the tile is in the challenge
        if(p1SlotsCh.contains(slotParent)){
            //And contains a joker (score 0), reset to a joker tile before move
            if(Integer.valueOf(tileScore.getText()) == 0){
                tileValue.setText("");
                for(ISlot slot : this.game.getMySide().getChallenge().getSlots()){
                    if(!slot.isEmpty()){
                        if(slot.getTile().getId() == tileId){
                            slot.getTile().setValue('#');
                        }
                    }
                }
            }
        } else if (tileValue.getText().equals("") && numberTilesOnChallengeRack != Constants.CHALLENGE_SIZE){
            Character result = getCharacterSelect();
            if(result == null){
                cancelMove = true;
            }else{
                if(p1TilesSr.contains(slotParent)){
                    for(ITile t : this.game.getMySide().getChallenge().getSwapRack().getTiles()){
                        if(t.getId() == tileId){
                            t.setValue(result);
                        }
                    }
                }else{
                    for(ITile t : this.game.getMySide().getPlayerRack().getTiles()){
                        if(t.getId() == tileId){
                            t.setValue(result);
                        }
                    }

                }
                tileValue.setText(String.valueOf(result));
            }
        }

        // Apply move if not cancelled
        if(!cancelMove){
            move(tileSelect, slotParent);
        }

        if (numberTilesOnChallengeRack == 0) {
            shuffleButton.setText("Melanger");
        } else {
            shuffleButton.setText("Effacer");
        }
    }

    /**
     * Opens a character selection window and waits for user selection
     * @return the selected character, or null if cancelled or window closed
     */
    private Character getCharacterSelect() {
        final Stage popUp = new Stage();
        popUp.initOwner(MainApp.getStage());
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/fxml/characterSelect.fxml"));
        BorderPane c;
        CharacterSelectController controller = new CharacterSelectController(alphabet);
        try {
            loader.setController(controller);
            c = loader.load();
            Scene testScene = new Scene(c);
            popUp.setScene(testScene);

            popUp.setTitle("Selection du Joker");
            popUp.sizeToScene();
            popUp.setResizable(false);

            popUp.initModality(Modality.APPLICATION_MODAL);
            popUp.showAndWait();
            return controller.getSelectedChar();
        } catch (IOException ex) {
            Logger.getLogger(GameScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @param tileSelect
     * @param slotParent
     */
    private void move(AnchorPane tileSelect, StackPane slotParent) {
        int idTile = Integer.valueOf(((Label) tileSelect.getChildren().get(2)).getText());
        StackPane destination = null;

        if (p1SlotsPr.contains(slotParent)) {
            // move to challenge from player rack
            destination = firstSlotEmpty(p1SlotsCh);
            if (null != destination) {
                addTileToSlot(destination, tileSelect);
                numberTilesOnChallengeRack++;

                // Move tile in Logic game
                ITile tile = this.game.getMySide().getPlayerRack().removeTile(idTile);
                this.game.getMySide().getChallenge().addTile(tile);
            }
        } else if (p1SlotsSr.contains(slotParent)) {
            // Move to challenge from swap rack
            destination = firstSlotEmpty(p1SlotsCh);
            if (null != destination) {
                addTileToSlot(destination, tileSelect);
                numberTilesOnChallengeRack++;

                // Move tile in Logic game
                ITile tile = this.game.getMySide().getChallenge().getSwapRack().removeTile(idTile);
                this.game.getMySide().getChallenge().addTile(tile);
            }
        } else {
            // Move to swapRack from challenge
            if (p1TilesSr.contains(tileSelect)) {
                // Maj GUI
                destination = firstSlotEmpty(p1SlotsSr);
                addTileToSlot(destination, tileSelect);
                numberTilesOnChallengeRack--;

                // Maj Logic
                ITile tile = this.game.getMySide().getChallenge().getTileById(idTile);
                this.game.getMySide().getChallenge().getSwapRack().addTile(tile);
            } else {
                // Move to player rack from challenge

                // Maj GUI
                destination = firstSlotEmpty(p1SlotsPr);
                addTileToSlot(destination, tileSelect);
                numberTilesOnChallengeRack--;

                // Maj Logic
                ITile tile = this.game.getMySide().getChallenge().getTileById(idTile);
                this.game.getMySide().getPlayerRack().addTile(tile);
            }
        }
        majWordAlyzer();
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
        // Tiles challenge
        addConentListAnchorePane(p2TilesCh, false, p2TileCh1, p2TileCh2, p2TileCh3, p2TileCh4, p2TileCh5, p2TileCh6, p2TileCh7);

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

    private void setVisible(List<AnchorPane> tiles, boolean isVisible) {
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

