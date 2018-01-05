package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.Api;
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
            }

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
        Object source = e.getSource();
        List<String> choice = null;
        if(source.equals(newGamePlayer)){
            choice = Dialog.getInstance().newGame();
        }else {
            choice = new LinkedList<>();
            String selection = Dialog.getInstance().choicesBoxDialog("Choix de langue",
                    "Veuillez choisir la langue du tournoi", "Langue",
                    UtilStringReference.LANG_FR, UtilStringReference.LANG_EN);

            switch (selection) {
                case UtilStringReference.LANG_FR:
                    lang = "fr";
                    break;
                case UtilStringReference.LANG_EN:
                    lang = "en";
                    break;
            }
        }

        if (null != choice) {
            if(source.equals(newGamePlayer)){
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

            if(e.getSource().equals(newGamePlayer)){
                if(type != null && type.equals("Contre un ami")){
                    try {
                        dto.setType(ModeType.FRIEND_DUEL);

                        // Dialog to select the opponent
                        String result = Dialog.getInstance().choiceNameOpponent();

                        if (result != null){
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

                try {
                    ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                    System.out.println("Tournoi envoyé au serveur");
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

                List<String> participants = Dialog.getInstance().getFriendlyTournamentParticipants();
                if(!participants.isEmpty()){
                    dto.setParticipants(participants);
                    try {
                        ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                    } catch (TokenNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    System.out.println("Le tournoi n'a pas été créé.");
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
            String endpoint = null;
            if(listGamesDuel.getListView().equals(games)){
                endpoint = listGamesDuel.getDtos().get(games.getSelectionModel().getSelectedIndex()).getEndpoint();
            }else if(listGamesDuelWait.getListView().equals(games)){
                endpoint = listGamesDuelWait.getDtos().get(games.getSelectionModel().getSelectedIndex()).getEndpoint();
            }else{
                endpoint = listGamesDuelFinish.getDtos().get(games.getSelectionModel().getSelectedIndex()).getEndpoint();
            }
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
            ModeSummaryDto modeSummaryDto = listGamesTournamentCompetition.getDtos().get(0);
            TournamentModeDto tmDto = null;
            try {
                tmDto = (TournamentModeDto) ModeApi.getMode(modeSummaryDto.getEndpoint());
                setupGraphicalTournament(tmDto, labelNumber, labelScore, labelClassement, labelChance, showDetailsComp);
            } catch (TokenNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            paneTournamentComp.setVisible(false);
            competitiveTournamentVbox.setVisible(true);
        }
    }

    private void setupGraphicalTournament(TournamentModeDto tmDto, Label labelNumber, Label labelScore, Label labelClassement, Label labelChance, Accordion accordion) {
        //Clear current view
        for(TitledPane tp : accordion.getPanes()){
            ((ListView)((AnchorPane)tp.getContent()).getChildren().get(0)).getItems().clear();
        }
        labelNumber.setText("Participants : " + tmDto.getParticipants().size() + "/20");
        List<TournamentModeDto.UserScore> globalScores = tmDto.getPlayerScoreForGlobal();
        int myScore = 0;
        //Find my score in global scores
        for(TournamentModeDto.UserScore us : globalScores){
            if(us.getUser().getName().equals(LoginController.currentUser)){
                myScore = us.getScore();
                break;
            }
        }
        labelScore.setText("Score : " + myScore);
        int myRank = 1;
        //Find players with higher scores than mine
        for(TournamentModeDto.UserScore us : globalScores){
            if(!(us.getUser().getName().equals(LoginController.currentUser))){
                if(us.getScore() > myScore){
                    myRank++;
                }
            }
        }

        labelClassement.setText("Classement : " + myRank);
        labelChance.setText(tmDto.getNbGameRemaining() + "/2");
        ObservableList<TitledPane> panes = accordion.getPanes();

        //Add ranking
        AnchorPane rankPane = (AnchorPane) panes.get(0).getContent();
        ListView<String> rankListView = (ListView<String>) rankPane.getChildren().get(0);
        for(int i = 0; i < globalScores.size(); ++i) {
            rankListView.getItems().add(i + 1 + ": " + globalScores.get(i).getUser().getName() +
                    " " + globalScores.get(i).getScore() + " pts.");
        }

        //Add days
        for(int i = 1; i < accordion.getPanes().size(); ++i){
            List<TournamentModeDto.UserScore> dailyScores = tmDto.getPlayerScoreForDay(i - 1);
            TitledPane dayTitlePane = accordion.getPanes().get(i);
            int myCurrentDayScore = 0;
            for(TournamentModeDto.UserScore us : dailyScores){
                if(us.getUser().getName().equals(LoginController.currentUser)){
                    myCurrentDayScore = us.getScore();
                    break;
                }
            }
            dayTitlePane.setText("Jour " + i + " - " + " Score " + myCurrentDayScore);
            AnchorPane dayPane = (AnchorPane)dayTitlePane.getContent();
            ListView<String> dailyRankListView = (ListView<String>) dayPane.getChildren().get(0);
            for(int j = 0; j < dailyScores.size(); ++j) {
                dailyRankListView.getItems().add(j + 1 + ": " + dailyScores.get(j).getUser().getName() +
                        " " + dailyScores.get(j).getScore() + " pts.");
            }
        }


    }

    private void showDetailsTournamentFriends(ListView list){
        // Utilisé pour récupérer les infos relative au tournoi sélectionné
        String nameTournament = (String) list.getSelectionModel().getSelectedItem();
        System.out.println(nameTournament);

        if(!listGamesTournamentsFriends.getListView().getItems().isEmpty()) {
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
                e.printStackTrace();
            }

        }else{
            paneTournamentFriends.setVisible(false);
            friendsTournamentVbox.setVisible(true);
        }

    }

    @FXML
    private void playTournamentComp(){
        if(!listGamesTournamentCompetition.getListView().getItems().isEmpty()){
            ModeSummaryDto modeSummaryDto = listGamesTournamentCompetition.getDtos().get(0);
            try {
                TournamentModeDto tmDto = (TournamentModeDto)ModeApi.getMode(modeSummaryDto.getEndpoint());
                if(tmDto.getGame() == null){
                    Object o = Api.post(tmDto.getGames(), null, GameDto.class);
                    this.selectGame = (GameDto) o;
                }else{
                    this.selectGame = GameApi.getGame(tmDto.getGame().getId());
                }
                handleGotoGame();
            } catch (TokenNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void backTournamentFriend(){
        paneTournamentFriends.setVisible(false);
        friendsTournamentVbox.setVisible(true);
    }

    @FXML
    private void playTournamentFriend(){
        if(!listGamesTournamentsFriends.getListView().getItems().isEmpty()){
            ModeSummaryDto modeSummaryDto = listGamesTournamentCompetition.getDtos().get(listGamesTournamentsFriends.getListView().getSelectionModel().getSelectedIndex());
            try {
                TournamentModeDto tmDto = (TournamentModeDto)ModeApi.getMode(modeSummaryDto.getEndpoint());
                if(tmDto.getGame() == null){
                    Object o = Api.post(tmDto.getGames(), null, GameDto.class);
                    this.selectGame = (GameDto) o;
                }else{
                    this.selectGame = GameApi.getGame(tmDto.getGame().getId());
                }
                handleGotoGame();
            } catch (TokenNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
