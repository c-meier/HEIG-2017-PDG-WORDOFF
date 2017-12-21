package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.GameApi;
import ch.heigvd.wordoff.client.Exception.*;
import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.client.Logic.Game;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.client.Util.ListCustom;
import ch.heigvd.wordoff.client.Util.UtilStringReference;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.GameSummaryDto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    // Classe de test
    private Game gameTest = new Game();
    private GameDto gameTestDto = gameTest.getGameDto();

    // GameDto selected to list
    private GameDto selectGame = null;
    // list de GameSummaryDto
    private List<GameSummaryDto> gamesSummaryDto = new LinkedList<>();
    private long myId;
    // Listes des games triées
    private ListCustom listGamesDuel;
    private ListCustom listGamesDuelWait;
    private ListCustom listGamesDuelFinish;
    private ListCustom listGamesTournamentsFriends;
    private ListCustom listGamesTournamentCompetition;

    // Vbox to display diff games
    @FXML
    private VBox vBoxgamesPlayer =  new VBox();
    @FXML
    private VBox vBoxgamesPlayerWait = new VBox();
    @FXML
    private VBox vBoxgamesPlayerFinish = new VBox();
    @FXML
    private VBox vBoxgamesTurnamentFriend =  new VBox();
    @FXML
    private Accordion accordionTournament = new Accordion();

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

        listGamesDuel.getListView().setOnMouseClicked(eventGoToGame);
        listGamesDuelWait.getListView().setOnMouseClicked(eventGoToGame);
        listGamesDuelFinish.getListView().setOnMouseClicked(eventGoToGame);

        sortGames();
    }

    private void sortGames(){
        try {
            gamesSummaryDto = GameApi.retrieveGames();
            // TODO trier les différentes games pour les mettre dans les bonnes listes
            for (GameSummaryDto dto : gamesSummaryDto) {
                // Ajoute les référence à la bonne liste (trie par le mode)
                listGamesDuel.addGame(dto);
                // TODO switch sur dto.getMode()
                // cas duel : listGamesDuel.addGame(dto)
                // cas duel en attente :  listGamesDuelWait.addGame(dto);
                // cas duel fini : listGamesDuelFinish.addGame(dto);

                // cas tournament et tournamentFriend :
                // Exemple pour construire les tournois

                // Intérieur de l'accordéon (ouvert)
                VBox vBox = new VBox();                                     // Layout
                ListCustom listGames = new ListCustom(vBox);                // Mise en page du VBox

                // Titre des tournois (menu pouvant être ouvert)
                TitledPane titledPane = new TitledPane();
                titledPane.setText(dto.getOtherPlayer().getName());         // Nom du tournoi
                titledPane.setContent(vBox);                                // Le détail du tournoi

                accordionTournament.getPanes().add(titledPane);             // Accroche le tournoi à l'accordéon

                // Partie logic de référence pour lancer un game sélectionné
                listGames.addGameAndUpdate(dto);                            // Ajouter la game à la liste de référence dto
                listGames.getListView().setOnMouseClicked(eventGoToGame);   // Ajouter le listener
            }

            // TODO update des listes
            listGamesDuel.addGameAndUpdate(this.gameTest.getGameSummaryDtoList().get(0));


        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalInformation(UtilStringReference.ERROR + " " + e.getMessage());
        } catch (UnauthorizedException e) {
            Dialog.getInstance().signalInformation(UtilStringReference.ERROR + " "+ e.getMessage());
        } catch (HTTPException e) {
            Dialog.getInstance().signalInformation(UtilStringReference.ERROR + " " + e.getMessage());
        }

    }

    @FXML
    private void newGame() {
        String langSelect = "";
        // TODO récupérer les bons ids
        long myId = 1;
        long otherId = 2;

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

            List<Long> playersId = new LinkedList();
            playersId.add(myId);
            playersId.add(otherId);

            System.out.println("Demande de créer une nouvelle partie : " + langSelect);
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
            Long selectId = gamesSummaryDto.get(games.getSelectionModel().getSelectedIndex()).getId();
            try {
                selectGame = GameApi.getGame(selectId);
                System.out.println(selectId);
                handleGotoGame();
            } catch (TokenNotFoundException e) {
                //e.printStackTrace();
                Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
            }
        }
    }

}
