/*
 * File: MainMenuController.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.*;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Exception.UnauthorizedException;
import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.client.Util.ListCustom;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.client.Util.UtilStringReference;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.Mode.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static ch.heigvd.wordoff.common.Constants.MAX_GAMES_PER_DAY;
import static ch.heigvd.wordoff.common.Constants.MAX_USER_IN_TOURNAMENT;

/**
 * JavaFX controller for the main menu screen.
 */
public class MainMenuController implements Initializable {

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
    private Label labelNumberFriend, labelScoreFriend, labelClassementFriend, labelChanceFriend;
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
    @FXML
    private Label labelInvitation;

    private MeDto meDto;

    @FXML
    private Timeline UIrefresher;

    // Handler to go to a game
    private EventHandler<MouseEvent> eventGoToGame = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent click) {
            ListView list = (ListView) click.getSource(); // Source du clique
            if (click.getClickCount() == 2) {
                goToGame(list);
            }
        }
    };

    // Handler to show tournament details
    private EventHandler<MouseEvent> eventShowDetails = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent click) {
            if (click.getClickCount() == 2) {
                showDetailsTournamentComp();
            }
        }
    };

    // Handler to show friendly tournament details
    private EventHandler<MouseEvent> eventShowDetailsFriends = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent click) {
            ListView list = (ListView) click.getSource(); // Source du clique
            if (click.getClickCount() == 2) {
                showDetailsTournamentFriends(list);
            }
        }
    };

    /**
     * Goes to current game (selectedGame attribute)
     */
    private void handleGotoGame() {
        if (selectGame != null && !selectGame.isEnded()) {
            FXMLLoader loader = getLoader("/fxml/gameScreen.fxml");
            Scene scene = getScene(loader);

            GameScreenController controller = loader.getController();
            controller.setGame(this.selectGame);
            controller.setAlphabet(LetterApi.retrieveLetters(selectGame.getLang()));

            changeScene(scene);
        } else {
            Dialog.getInstance().signalError("Cette partie est déjà terminée.");
        }
    }

    /**
     * Goes to messages screen.
     *
     * @param event
     */
    @FXML
    private void handleGotoMessages(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/messages.fxml");
        changeScene(getScene(loader));
    }

    /**
     * Goes to settings screen
     *
     * @param event
     */
    @FXML
    private void handleGotoSettings(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/settings.fxml");
        changeScene(getScene(loader));
    }

    /**
     * Goes to invitations screen
     *
     * @param event
     */
    @FXML
    private void handleGotoInvitations(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/invitations.fxml");
        changeScene(getScene(loader));
    }

    /**
     * Goes to invitations screen via invitations label
     */
    @FXML
    private void handleGotoInvitationsLabel() {
        FXMLLoader loader = getLoader("/fxml/invitations.fxml");
        changeScene(getScene(loader));
    }

    // Envoi la scène au MainApp pour changer la scene dans le stage
    private void changeScene(Scene scene) {
        UIrefresher.stop();
        UIrefresher = null;
        MainApp.changeScene(scene);
    }

    // Récupère le fxml loader depuis le path
    private FXMLLoader getLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        return loader;
    }

    // Creates scene from loader
    private Scene getScene(FXMLLoader loader) {
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {

        }
        return new Scene(root);
    }

    /**
     * Called when initializing a JavaFX controller
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Sets the state of the main menu by connection to the server and
     * retrieving a MeDto and setting all handlers to correct lists
     */
    public void setState() {

        UIrefresher = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    meDto = MeApi.getCurrentUser();
                    listGamesDuel.clear();
                    listGamesDuelFinish.clear();
                    listGamesDuelWait.clear();
                    listGamesTournamentCompetition.clear();
                    int selectedFriendIndex = listGamesTournamentsFriends.getListView().getSelectionModel().getSelectedIndex();
                    listGamesTournamentsFriends.clear();
                    sortGames();
                    listGamesTournamentsFriends.getListView().getSelectionModel().select(selectedFriendIndex);
                } catch (TokenNotFoundException e) {
                    Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
                }
            }
        }));
        UIrefresher.setCycleCount(Timeline.INDEFINITE);
        UIrefresher.play();

        try {
            meDto = MeApi.getCurrentUser();
            labelInvitation.setText(String.valueOf((Api.get(meDto.getInvitations())).size()) + " notifications");
        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
        }

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

    /**
     * Sorts all retrieved modes into the correct lists
     */
    private void sortGames() {
        try {
            modeSummaryDtos = ModeApi.retrieveModes();
            for (ModeSummaryDto dto : modeSummaryDtos) {
                switch (dto.getType()) {
                    case FRIEND_DUEL:
                    case RANDOM_DUEL:
                        if (dto.isEnded()) {
                            listGamesDuelFinish.addGame(dto);
                        } else if (dto.isActive()) {
                            listGamesDuel.addGame(dto);
                        } else {
                            listGamesDuelWait.addGame(dto);
                        }
                        break;
                    case FRIENDLY_TOURNAMENT:
                        listGamesTournamentsFriends.addGame(dto);

                        break;
                    case COMPETITIVE_TOURNAMENT:
                        listGamesTournamentCompetition.addGame(dto);
                        break;
                }
            }

        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
        } catch (UnauthorizedException e) {
            Dialog.getInstance().signalInformation(UtilStringReference.ERROR + " " + e.getMessage());
        } catch (HTTPException e) {
            Dialog.getInstance().signalInformation(UtilStringReference.ERROR + " " + e.getMessage());
        }

    }

    /**
     * Create a new game in any mode
     *
     * @param e a mouse click event
     */
    @FXML
    private void newGame(MouseEvent e) {
        String lang = "";
        String type = null;
        Object source = e.getSource();
        List<String> choice = null;
        if (source.equals(newGamePlayer)) {
            choice = Dialog.getInstance().newGame();
        } else {
            if (!source.equals(newTournament) || listGamesTournamentCompetition
                    .getListView().getItems().isEmpty()) { //Check if competitive tournament already exists
                choice = new LinkedList<>();
                String selection = Dialog.getInstance().choicesBoxDialog("Choix de langue",
                        "Veuillez choisir la langue du tournoi", "Langue",
                        UtilStringReference.LANG_FR, UtilStringReference.LANG_EN);
                if (selection == null) {
                    return;
                }
                switch (selection) {
                    case UtilStringReference.LANG_FR:
                        lang = "fr";
                        break;
                    case UtilStringReference.LANG_EN:
                        lang = "en";
                        break;
                }
            } else {
                Dialog.getInstance().signalError("Un tournoi est deja en cours");
            }
        }

        if (null != choice) {
            if (source.equals(newGamePlayer)) {
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

            }


            CreateModeDto dto = new CreateModeDto();
            dto.setLang(lang);
            dto.setParticipants(new LinkedList<>());

            if (e.getSource().equals(newGamePlayer)) {
                if (type != null && type.equals("Contre un ami")) {
                    try {
                        dto.setType(ModeType.FRIEND_DUEL);

                        // Dialog to select the opponent
                        String result = Dialog.getInstance().choiceNameOpponent("Entrez le nom de votre adversaire");

                        if (result != null) {
                            dto.addParticpant(result);
                            dto.setName(result);
                            ModeSummaryDto modeSummaryDto = null;
                            try {
                                modeSummaryDto = ModeApi.createMode(dto);
                            } catch (HTTPException h) {
                                Dialog.getInstance().signalError(UtilStringReference.PLAYER_NOT_FOUND);
                                return;
                            }
                            if (modeSummaryDto.isActive()) {
                                listGamesDuel.addGame(modeSummaryDto);
                            } else {
                                listGamesDuelWait.addGame(modeSummaryDto);
                            }

                        }

                    } catch (TokenNotFoundException e1) {
                        Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
                    }
                } else {
                    try {
                        dto.setType(ModeType.RANDOM_DUEL);
                        ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                        if (modeSummaryDto.isActive()) {
                            listGamesDuel.addGame(modeSummaryDto);
                        } else {
                            listGamesDuelWait.addGame(modeSummaryDto);
                        }
                    } catch (TokenNotFoundException e1) {
                        Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
                    } catch (NoSuchElementException ex) {
                        Dialog.getInstance().signalError(ex.getMessage());
                    } catch (Exception exep) {
                        Dialog.getInstance().signalError(exep.getMessage());
                    }
                }
            } else if (e.getSource().equals(newTournament)) { //new competitive
                dto.setType(ModeType.COMPETITIVE_TOURNAMENT);

                try {
                    ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                    listGamesTournamentCompetition.addGame(modeSummaryDto);
                    System.out.println("Tournoi envoyé au serveur");
                } catch (TokenNotFoundException e1) {
                    Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
                }

            } else { // new friendly tournament
                dto.setType(ModeType.FRIENDLY_TOURNAMENT);
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Nouveau tournoi amical");
                dialog.setHeaderText("Entrez le nom du tournoi");
                dialog.setContentText("Nom:");

                DialogPane dialogPane = dialog.getDialogPane();
                ObservableList<String> st = dialogPane.getStylesheets();
                dialogPane.getStylesheets().add(
                        getClass().getResource("/styles/Style_alert.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    dto.setName(result.get());
                } else {
                    return;
                }

                List<String> participants = Dialog.getInstance().getFriendlyTournamentParticipants();
                if (!participants.isEmpty()) {
                    dto.setParticipants(participants);
                    try {
                        ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                        listGamesTournamentsFriends.addGame(modeSummaryDto);
                    } catch (TokenNotFoundException e1) {
                        Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
                    }
                } else {
                    System.out.println("Le tournoi n'a pas été créé.");
                }


            }

        }
    }


    /**
     * Allows user to go to a game using the handleGoToGame method.
     *
     * @param games ListView of games
     */
    @FXML
    private void goToGame(ListView games) {
        // Partie venant du serveur
        String endpoint = null;
        if (listGamesDuel.getListView().equals(games)) {
            endpoint = listGamesDuel.getDtos().get(games.getSelectionModel().getSelectedIndex()).getEndpoint();
        } else if (listGamesDuelWait.getListView().equals(games)) {
            endpoint = listGamesDuelWait.getDtos().get(games.getSelectionModel().getSelectedIndex()).getEndpoint();
        } else {
            endpoint = listGamesDuelFinish.getDtos().get(games.getSelectionModel().getSelectedIndex()).getEndpoint();
        }
        try {
            ModeDto mode = ModeApi.getMode(endpoint);
            if (mode.getGame() != null && !mode.getName().equalsIgnoreCase("pas encore d'adversaire")) {
                selectGame = GameApi.getGame(mode.getGame().getId());
                handleGotoGame();
            } else {
                Dialog.getInstance().signalInformation("Pas encore d'adversaire pour cette partie.");
            }
        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
        }

    }

    /**
     * Shows details of a competitive tournament
     */
    private void showDetailsTournamentComp() {
        if (!listGamesTournamentCompetition.getListView().getItems().isEmpty()) {
            paneTournamentComp.setVisible(true);
            competitiveTournamentVbox.setVisible(false);
            ModeSummaryDto modeSummaryDto = listGamesTournamentCompetition.getDtos().get(0);
            TournamentModeDto tmDto = null;
            try {
                tmDto = (TournamentModeDto) ModeApi.getMode(modeSummaryDto.getEndpoint());
                setupGraphicalTournament(tmDto, labelNumber, labelScore, labelClassement, labelChance, showDetailsComp);
            } catch (TokenNotFoundException e) {
                Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
            }

        } else {
            paneTournamentComp.setVisible(false);
            competitiveTournamentVbox.setVisible(true);
        }
    }

    private void setupGraphicalTournament(TournamentModeDto tmDto, Label labelNumber, Label labelScore, Label labelClassement, Label labelChance, Accordion accordion) {
        //Clear current view
        for (TitledPane tp : accordion.getPanes()) {
            ((ListView) ((AnchorPane) tp.getContent()).getChildren().get(0)).getItems().clear();
        }
        labelNumber.setText("Participants : " + tmDto.getParticipants().size() + "/" + MAX_USER_IN_TOURNAMENT);
        List<TournamentModeDto.UserScore> globalScores = tmDto.getPlayerScoreForGlobal();
        int myScore = 0;
        //Find my score in global scores
        for (TournamentModeDto.UserScore us : globalScores) {
            if (us.getUser().getName().equals(meDto.getSelf().getName())) {
                myScore = us.getScore();
                break;
            }
        }
        labelScore.setText("Score : " + myScore);
        int myRank = 1;
        //Find players with higher scores than mine
        for (TournamentModeDto.UserScore us : globalScores) {
            if (!(us.getUser().getName().equals(meDto.getSelf().getName()))) {
                if (us.getScore() > myScore) {
                    myRank++;
                }
            }
        }

        labelClassement.setText("Classement : " + myRank);
        labelChance.setText("Tentatives restantes : " + tmDto.getNbGameRemaining() + "/" + MAX_GAMES_PER_DAY);
        ObservableList<TitledPane> panes = accordion.getPanes();

        //Add ranking
        AnchorPane rankPane = (AnchorPane) panes.get(0).getContent();
        ListView<String> rankListView = (ListView<String>) rankPane.getChildren().get(0);
        for (int i = 0; i < globalScores.size(); ++i) {
            rankListView.getItems().add(i + 1 + ": " + globalScores.get(i).getUser().getName() +
                    " " + globalScores.get(i).getScore() + " pts.");
        }

        //Add days
        for (int i = 1; i < accordion.getPanes().size(); ++i) {
            List<TournamentModeDto.UserScore> dailyScores = tmDto.getPlayerScoreForDay(i - 1);
            TitledPane dayTitlePane = accordion.getPanes().get(i);
            int myCurrentDayScore = 0;
            for (TournamentModeDto.UserScore us : dailyScores) {
                if (us.getUser().getName().equals(meDto.getSelf().getName())) {
                    myCurrentDayScore = us.getScore();
                    break;
                }
            }
            dayTitlePane.setText("Jour " + i + " - " + " Score " + myCurrentDayScore);
            AnchorPane dayPane = (AnchorPane) dayTitlePane.getContent();
            ListView<String> dailyRankListView = (ListView<String>) dayPane.getChildren().get(0);
            for (int j = 0; j < dailyScores.size(); ++j) {
                dailyRankListView.getItems().add(j + 1 + ": " + dailyScores.get(j).getUser().getName() +
                        " " + dailyScores.get(j).getScore() + " pts.");
            }
        }


    }

    /**
     * Shows details of a friendly tournament
     *
     * @param list
     */
    private void showDetailsTournamentFriends(ListView list) {
        // Utilisé pour récupérer les infos relative au tournoi sélectionné
        String nameTournament = (String) list.getSelectionModel().getSelectedItem();
        System.out.println(nameTournament);

        if (!listGamesTournamentsFriends.getListView().getItems().isEmpty()) {
            paneTournamentFriends.setVisible(true);
            friendsTournamentVbox.setVisible(false);

            ModeSummaryDto modeSummaryDto = listGamesTournamentsFriends.getDtos().get(
                    listGamesTournamentsFriends.getListView().getSelectionModel().getSelectedIndex()
            );
            TournamentModeDto tmDto = null;
            try {
                tmDto = (TournamentModeDto) ModeApi.getMode(modeSummaryDto.getEndpoint());
                setupGraphicalTournament(tmDto, labelNumberFriend, labelScoreFriend, labelClassementFriend, labelChanceFriend, showDetailsFriend);
            } catch (TokenNotFoundException e) {
                Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
            }

        } else {
            paneTournamentFriends.setVisible(false);
            friendsTournamentVbox.setVisible(true);
        }

    }

    /**
     * Play a tournament game.
     */
    @FXML
    private void playTournamentComp() {
        if (!listGamesTournamentCompetition.getListView().getItems().isEmpty()) {
            ModeSummaryDto modeSummaryDto = listGamesTournamentCompetition.getDtos().get(0);
            playTournamentGame(modeSummaryDto);
        }
    }

    private void playTournamentGame(ModeSummaryDto modeSummaryDto) {
        try {
            TournamentModeDto tmDto = (TournamentModeDto) ModeApi.getMode(modeSummaryDto.getEndpoint());
            if (tmDto.getGame() == null) {
                this.selectGame = Api.post(tmDto.getGames(), null, GameDto.class);
            } else {
                if (tmDto.getGame().isEnded()) {
                    if (tmDto.getNbGameRemaining() > 0) {
                        boolean result = Dialog.getInstance().popUpYesNo("Utiliser une tentative et réessayer?");
                        if (!result) {
                            return;
                        } else {
                            this.selectGame = Api.post(tmDto.getGames(), null, GameDto.class);
                        }
                    } else {
                        Dialog.getInstance().signalError("Vous n'avez plus de tentatives pour aujourd'hui!");
                    }
                } else {
                    this.selectGame = GameApi.getGame(tmDto.getGame().getId());
                }
            }
            handleGotoGame();
        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
        } catch (Exception e) {
            Dialog.getInstance().signalError(e.getMessage());
        }

    }

    @FXML
    private void backTournamentFriend() {
        paneTournamentFriends.setVisible(false);
        friendsTournamentVbox.setVisible(true);
    }

    @FXML
    private void playTournamentFriend() {
        if (!listGamesTournamentsFriends.getListView().getItems().isEmpty()) {
            ModeSummaryDto modeSummaryDto = listGamesTournamentsFriends.getDtos().get(listGamesTournamentsFriends.getListView().getSelectionModel().getSelectedIndex());
            playTournamentGame(modeSummaryDto);
        }
    }

    /**
     * Log out the user
     */
    @FXML
    private void handleLogout() {
        TokenManager.deleteToken();
        FXMLLoader loader = getLoader("/fxml/login.fxml");
        changeScene(getScene(loader));
    }
}
