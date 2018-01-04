package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.GameApi;
import ch.heigvd.wordoff.client.Api.ModeApi;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Exception.UnauthorizedException;
import ch.heigvd.wordoff.client.Api.LetterApi;
import ch.heigvd.wordoff.client.Exception.*;
import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.client.Logic.Game;
import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.client.Util.ListCustom;
import ch.heigvd.wordoff.client.Util.UtilStringReference;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.GameSummaryDto;
import ch.heigvd.wordoff.common.Dto.Mode.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainMenuController implements Initializable {
    // Classe de test
    private Game gameTest = new Game();
    private GameDto gameTestDto = gameTest.getGameDto();

    // GameDto selected to list
    private GameDto selectGame = null;
    // list de GameSummaryDto
    private List<ModeSummaryDto> modeSummaryDtos = new LinkedList<>();
    private long myId;
    // Listes des games triées
    private ListCustom listGamesDuel;
    private ListCustom listGamesDuelWait;
    private ListCustom listGamesDuelFinish;
    private ListCustom listGamesTournamentsFriends;
    private ListCustom listGamesTournamentCompetition;

    // Vbox to display diff games
    @FXML
    private VBox vBoxgamesPlayer;
    @FXML
    private VBox vBoxgamesPlayerWait;
    @FXML
    private VBox vBoxgamesPlayerFinish;
    @FXML
    private VBox competitiveTournamentVbox;
    @FXML
    private VBox friendsTournamentVbox;


    /* Button to start new game */
    @FXML
    private Button newGamePlayer;
    @FXML
    private Button newTournament;
    @FXML
    private Button newTournamentFriend;
    //@FXML
   // private HBox resultTournamentComp;
    @FXML
    private Label labelScore, labelClassement, labelNumber, labelChance;
    @FXML
    private Accordion showDetailsComp;
    @FXML
    private Accordion showDetailsFriend;
    @FXML
    private GridPane paneTournamentComp;
    @FXML
    private GridPane paneTournamentFriends;
    @FXML
    private AnchorPane parentPaneTournamentComp;
    @FXML
    private AnchorPane parentPaneTournamentFriends;

    // MouseEvent d'un double clique, appelle l'ouverture de la game.
    private EventHandler<MouseEvent> eventGoToGame = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent click) {
            ListView list = (ListView) click.getSource(); // Source du clique
            if (click.getClickCount() == 2) {
                goToGame(list);
            }
        }
    };

    private EventHandler<MouseEvent>  eventShowDetails = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent click) {
            if (click.getClickCount() == 2) {
                showDetailsTournamentComp();
            }
        }
    };
    private EventHandler<MouseEvent>  eventShowDetailsFriends = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent click) {
            ListView list = (ListView) click.getSource(); // Source du clique
            if (click.getClickCount() == 2) {
                showDetailsTournamentFriends(list);
            }
        }
    };

    private void handleGotoGame() {
        FXMLLoader loader = getLoader("/fxml/gameScreen.fxml");
        Scene scene = getScene(loader);

        GameScreenController controller = loader.getController();
        controller.setGame(this.selectGame);
        controller.setAlphabet(LetterApi.retrieveLetters(selectGame.getLang()));

        changeScene(scene);
    }

    @FXML
    private void handleGotoMessages(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/messages.fxml");
        changeScene(getScene(loader));
    }

    @FXML
    private void handleGotoSettings(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/settings.fxml");
        changeScene(getScene(loader));
    }

    @FXML
    private void handleGotoInvitations(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/invitations.fxml");
        changeScene(getScene(loader));
    }

    @FXML
    private void handleGotoAlert(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/alertes.fxml");
        changeScene(getScene(loader));
    }

    // Envoi la scène au MainApp pour changer la scene dans le stage
    private void changeScene(Scene scene) {
        MainApp.changeScene(scene);
    }

    // Récupère le fxml loader depuis le path
    private FXMLLoader getLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        return loader;
    }

    // Création de la scène du loader
    private Scene getScene(FXMLLoader loader) {
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {

        }
        return new Scene(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setState() {
        // TODO Récupérer l'id joueur

        // Créations des listes et de leurs listener
        listGamesDuel = new ListCustom(vBoxgamesPlayer);
        listGamesDuelWait = new ListCustom(vBoxgamesPlayerWait);
        listGamesDuelFinish = new ListCustom(vBoxgamesPlayerFinish);
        listGamesTournamentCompetition = new ListCustom(competitiveTournamentVbox);
        listGamesTournamentsFriends = new ListCustom(friendsTournamentVbox);

        listGamesDuel.getListView().setOnMouseClicked(eventGoToGame);
        listGamesDuelWait.getListView().setOnMouseClicked(eventGoToGame);
        listGamesDuelFinish.getListView().setOnMouseClicked(eventGoToGame);

        listGamesTournamentCompetition.getListView().setOnMouseClicked(eventShowDetails);
        listGamesTournamentsFriends.getListView().setOnMouseClicked(eventShowDetailsFriends);
        sortGames();
    }

    private void sortGames(){
        try {
            modeSummaryDtos = ModeApi.retrieveModes();
            //For testing:
            //List<ModeSummaryDto> mode  = gameTest.getModeSummaryDtosList();
            // TODO trier les différentes games pour les mettre dans les bonnes listes
            for (ModeSummaryDto dto : modeSummaryDtos) {
                switch(dto.getType()){
                    case FRIEND_DUEL:
                    case RANDOM_DUEL:
                        if(dto.isEnded()){
                            listGamesDuelFinish.addGame(dto);
                        }else if (dto.isActive()){
                            listGamesDuel.addGame(dto);
                        }else{
                            listGamesDuelWait.addGame(dto);
                        }
                        break;
                    case FRIENDLY_TOURNAMENT:
                        listGamesTournamentsFriends.addGame(dto);

                        break;
                    case COMPETITIVE_TOURNAMENT:
                        listGamesTournamentCompetition.addGame(dto);
                     /*   TitledPane titledPane = new TitledPane();
                        titledPane.setText(dto.getName());
                        titledPane.setContent(competitiveTournamentVbox);*/
                        break;
                }

                //listGamesTournamentsFriends.updateView();
/*
                // cas tournament et tournamentFriend :
                // Exemple pour construire les tournois

                // Intérieur de l'accordéon (ouvert)
                VBox vBox = new VBox();                                     // Layout
                ListCustom listGames = new ListCustom(vBox);                // Mise en page du VBox

                // Titre des tournois (menu pouvant être ouvert)
                TitledPane titledPane = new TitledPane();
                titledPane.setText(dto.getName());         // Nom du tournoi
                titledPane.setContent(vBox);                                // Le détail du tournoi

                friendTournamentAccordion.getPanes().add(titledPane);             // Accroche le tournoi à l'accordéon

                // Partie logic de référence pour lancer un game sélectionné
                listGames.addGameAndUpdate(dto);                            // Ajouter la game à la liste de référence dto
                listGames.getListView().setOnMouseClicked(eventGoToGame);   // Ajouter le listener
*/
            }

            listGamesTournamentCompetition.addGame(gameTest.getModeSummaryDtosList().get(0));
            listGamesTournamentsFriends.addGame(gameTest.getModeSummaryDtosList().get(0));
            listGamesTournamentsFriends.addGame(gameTest.getModeSummaryDtosList().get(2));

        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalInformation(UtilStringReference.ERROR + " " + e.getMessage());
        } catch (UnauthorizedException e) {
            Dialog.getInstance().signalInformation(UtilStringReference.ERROR + " "+ e.getMessage());
        } catch (HTTPException e) {
            Dialog.getInstance().signalInformation(UtilStringReference.ERROR + " " + e.getMessage());
        }

    }

    @FXML
    private void newGame(MouseEvent e) {
        String lang = "";
        String type = null;

        List<String> choice = null;
        if(e.getSource().equals(newGamePlayer)){
            choice = Dialog.getInstance().newGame();
        }

        if (null != choice) {
            lang = choice.get(0);
            type = choice.get(1);

            switch (choice.get(0)) {
                case UtilStringReference.LANG_FR:
                    lang = "fr";
                    break;
                case UtilStringReference.LANG_EN:
                    lang = "en";
                    break;
            }

            System.out.println("Demande de créer une nouvelle partie : " + lang);

            CreateModeDto dto = new CreateModeDto();
            dto.setLang(lang);
            dto.setParticipants(new LinkedList<>());

            if(e.getSource().equals(newGamePlayer)){
                if(type != null && type.equals("Contre un ami")){
                    try {
                        dto.setType(ModeType.FRIEND_DUEL);

                        // Dialog to select the opponent
                        String result = Dialog.getInstance().choiceNameOpponent();

                        if (!result.isEmpty()){
                            dto.addParticpant(result);
                            dto.setName(result);
                            ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                            if(modeSummaryDto.isActive()){
                                listGamesDuel.addGame(modeSummaryDto);
                            } else {
                                listGamesDuelWait.addGame(modeSummaryDto);
                            }

                        }

                    } catch (TokenNotFoundException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        dto.setType(ModeType.RANDOM_DUEL);
                        ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                        if(modeSummaryDto.isActive()){
                            listGamesDuel.addGame(modeSummaryDto);
                        } else {
                            listGamesDuelWait.addGame(modeSummaryDto);
                        }
                    } catch (TokenNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchElementException ex){
                        Dialog.getInstance().signalError(ex.getMessage());
                    }catch(Exception exep){
                        Dialog.getInstance().signalError(exep.getMessage());
                    }
                }
            }else if (e.getSource().equals(newTournament)){ //new competitive
                dto.setType(ModeType.COMPETITIVE_TOURNAMENT);
                dto.setLang(lang);
                try {
                    ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                } catch (TokenNotFoundException e1) {
                    e1.printStackTrace();
                }

            }else { // new friendly tournament
                dto.setType(ModeType.FRIENDLY_TOURNAMENT);
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Entrez le nom du tournoi");
                dialog.setContentText("Nom:");

                DialogPane dialogPane = dialog.getDialogPane();
                ObservableList<String> st = dialogPane.getStylesheets();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/styles/Style_alert.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    dto.setName(result.get());
                }else{
                    dto.setName("Tournoi amical");
                }

                //TODO: fenetre avec choix parmis liste d'amis + entrée quelquconque d'utilisateur
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Creation du tournoi amical");
                alert.setHeaderText(null);
                alert.setContentText("Placeholder. Lorsque les méthodes de l'api seront disponibles, on pourra ici choisir " +
                        "parmi les amis et joueurs récents. Pour l'instant, one et two sont hardcodés dans ce tournoi.");

                alert.showAndWait();
                dto.addParticpant("one");
                dto.addParticpant("two");
                try {
                    ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                } catch (TokenNotFoundException e1) {
                    e1.printStackTrace();
                }

            }

        // TODO demande de créer la nuvelle partie au serveur en fonction du mode => récupérer la source de l'event

        }
    }




    @FXML
    private void goToGame(ListView games) {
        // Partie de test static
        if (games.getSelectionModel().getSelectedItem().equals("Game Test Static")) {
            selectGame = gameTest.getGameDto();
            handleGotoGame();
        } else {
            // Partie venant du serveur
            String endpoint = modeSummaryDtos.get(games.getSelectionModel().getSelectedIndex()).getEndpoint();
            try {
                ModeDto mode = ModeApi.getMode(endpoint);
                selectGame = GameApi.getGame(mode.getGame().getId());
                System.out.println(endpoint);
                handleGotoGame();
            } catch (TokenNotFoundException e) {
                //e.printStackTrace();
                Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
            }
        }
    }

    public void showDetailsTournamentComp(){
        if(!listGamesTournamentCompetition.getListView().getItems().isEmpty()){
            paneTournamentComp.setVisible(true);
            competitiveTournamentVbox.setVisible(false);
            // TODO récupérer les infos et remplir les listes au format
            // Position Joeur Score   =>   1. Marcel 320 pt

            for(TitledPane titledPane : showDetailsComp.getPanes()){
                AnchorPane pane = (AnchorPane) titledPane.getContent();
                System.out.println(titledPane.getText());
                ListView<String> test = (ListView<String>) pane.getChildren().get(0);
                test.getItems().add("Test 1");
                test.getItems().add("Test 2");
                test.getItems().add("Test 3");
                test.getItems().add("Test 1");
                test.getItems().add("Test 2");
                test.getItems().add("Test 3");
                test.getItems().add("Test 1");
                test.getItems().add("Test 2");
                test.getItems().add("Test 3");
            }
        }else{
            paneTournamentComp.setVisible(false);
            competitiveTournamentVbox.setVisible(true);
        }
    }

    private void showDetailsTournamentFriends(ListView list){
        // Utilisé pour récupérer les infos relative au tournoi sélectionné
        String nameTournament = (String) list.getSelectionModel().getSelectedItem();
        System.out.println(nameTournament);

        if(!listGamesTournamentsFriends.getListView().getItems().isEmpty()) {
            paneTournamentFriends.setVisible(true);
            friendsTournamentVbox.setVisible(false);

            // TODO avec les données serveur
            for(TitledPane titledPane : showDetailsFriend.getPanes()){
                AnchorPane pane = (AnchorPane) titledPane.getContent();
                System.out.println(titledPane.getText());
                ListView<String> test = (ListView<String>) pane.getChildren().get(0);
                test.getItems().add("Test 1");
                test.getItems().add("Test 2");
                test.getItems().add("Test 3");
                test.getItems().add("Test 1");
                test.getItems().add("Test 2");
                test.getItems().add("Test 3");
                test.getItems().add("Test 1");
                test.getItems().add("Test 2");
                test.getItems().add("Test 3");
            }
        }else{
            paneTournamentFriends.setVisible(false);
            friendsTournamentVbox.setVisible(true);
        }

    }

    @FXML
    private void playTournamentComp(){
        // TODO lancer la partie du tournoi compétitif
    }

    @FXML
    private void backTournamentFriend(){
        paneTournamentFriends.setVisible(false);
        friendsTournamentVbox.setVisible(true);
    }

    @FXML
    private void playTournamentFriend(){
        // TODO lancer la partie du tournoi amical ouvert
    }
}
