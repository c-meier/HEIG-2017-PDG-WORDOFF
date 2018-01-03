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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private Accordion friendTournamentAccordion;

    /* Button to start new game */
    @FXML
    private Button newGamePlayer;
    @FXML
    private Button newTournament;
    @FXML
    private Button newTournamentFriend;

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

        listGamesDuel.getListView().setOnMouseClicked(eventGoToGame);
        listGamesDuelWait.getListView().setOnMouseClicked(eventGoToGame);
        listGamesDuelFinish.getListView().setOnMouseClicked(eventGoToGame);
        listGamesTournamentCompetition.getListView().setOnMouseClicked(eventGoToGame);

        sortGames();
        listGamesDuelFinish.updateView();
        listGamesDuel.updateView();
        listGamesDuelWait.updateView();

       // listGamesTournamentCompetition.updateView();
      //  listGamesTournamentCompetition.updateView();
       // listGamesTournamentsFriends.updateView();
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
                        TitledPane titledPane = new TitledPane();
                        titledPane.setText(dto.getName());
                        titledPane.setContent(competitiveTournamentVbox);
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

            // TODO update des listes
            //listGamesDuel.addGameAndUpdate(this.gameTest.getGameSummaryDtoList().get(0));


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
        String langSelect = "";
        String type = null;

        if(e.getSource().equals(newGamePlayer)){
            type = Dialog.getInstance().choicesBoxDialog("Démarrer une nouvelle partie",
                    "Quel type de partie?",
                    "Type :",
                    "Adversaire aléatoire",
                    "Contre un ami");
        }

        String choice = Dialog.getInstance().choicesBoxDialog("Démarrer une nouvelle partie",
                UtilStringReference.INFOS_SELECT_LANGUAGE,
                "Langue : ",
                UtilStringReference.TEXT_PARAM_LANG.get(0),
                UtilStringReference.TEXT_PARAM_LANG.get(1));

        if (null != choice) {
            switch (choice) {
                case UtilStringReference.LANG_FR:
                    langSelect = "fr";
                    break;
                case UtilStringReference.LANG_EN:
                    langSelect = "en";
                    break;
                default:
                    return;
            }

            System.out.println("Demande de créer une nouvelle partie : " + langSelect);

            CreateModeDto dto = new CreateModeDto();
            dto.setLang(langSelect);
            dto.setParticipants(new LinkedList<>());

            if(e.getSource().equals(newGamePlayer)){
                if(type != null && type.equals("Contre un ami")){
                    try {
                        dto.setType(ModeType.FRIEND_DUEL);
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Entrez le nom de l'adversaire");
                        dialog.setContentText("Nom de l'adversaire:");

                        DialogPane dialogPane = dialog.getDialogPane();
                        ObservableList<String> st = dialogPane.getStylesheets();
                        dialogPane.getStylesheets().add(
                                getClass().getResource("/styles/Style_alert.css").toExternalForm());
                        dialogPane.getStyleClass().add("myDialog");

                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()){
                            dto.addParticpant(result.get());
                            dto.setName(result.get());
                            ModeSummaryDto modeSummaryDto = ModeApi.createMode(dto);
                            if(modeSummaryDto.isActive()){
                                listGamesDuel.addGameAndUpdate(modeSummaryDto);
                            } else {
                                listGamesDuelWait.addGameAndUpdate(modeSummaryDto);
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
                            listGamesDuel.addGameAndUpdate(modeSummaryDto);
                        } else {
                            listGamesDuelWait.addGameAndUpdate(modeSummaryDto);
                        }
                    } catch (TokenNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }else if (e.getSource().equals(newTournament)){ //new competitive
                dto.setType(ModeType.COMPETITIVE_TOURNAMENT);
                dto.setLang(langSelect);

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

}
