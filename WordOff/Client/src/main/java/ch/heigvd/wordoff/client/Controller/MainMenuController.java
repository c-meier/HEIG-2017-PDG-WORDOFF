package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.GameApi;
import ch.heigvd.wordoff.client.Exception.*;
import ch.heigvd.wordoff.client.MainApp;
import ch.heigvd.wordoff.client.Logic.Game;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.common.Dto.GameDto;
import ch.heigvd.wordoff.common.Dto.GameSummaryDto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

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

    @FXML
    ListView<String> gamesPlayer = new ListView<>();
    @FXML
    ListView<String> gamesPlayerWait = new ListView<>();
    @FXML
    ListView<String> gamesPlayerFinish = new ListView<>();
    @FXML
    ListView<String> gamesTurnamentComp = new ListView<>();
    @FXML
    ListView<String> gamesTurnamentFriend = new ListView<>();


    private void handleGotoGame() {
        FXMLLoader loader = getLoader("/fxml/gameScreen.fxml");
        Scene scene = getScene(loader);

        GameScreenController controller = loader.getController();
        controller.setGame(this.selectGame);

        changeScene(scene);
    }

    @FXML
    private void handleGotoProfile(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/profile.fxml");
        changeScene(getScene(loader));
    }

    @FXML
    private void handleGotoFriends(ActionEvent event) {
        FXMLLoader loader = getLoader("/fxml/friends.fxml");
        changeScene(getScene(loader));
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
        // Remplire les games disponnibles
        try {
            List<GameSummaryDto> list = GameApi.retrieveGames();
            for (GameSummaryDto dto : list) {
                gamesSummaryDto.add(dto);
                gamesPlayer.getItems().add(dto.getOtherPlayer().getName());
            }
        } catch (TokenNotFoundException e) {
            e.printStackTrace();
            System.out.println("TockerFoundException");
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            System.out.println("UnauthorizedException");
        } catch (HTTPException e) {
            e.printStackTrace();
            System.out.println("HTTPException");
        }

        gamesPlayer.getItems().add(this.gameTest.getGameSummaryDtoList().get(0).getOtherPlayer().getName());
        // Ajoute un listener sur la liste
        gamesPlayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    goToGame();
                }
            }
        });
    }

    @FXML
    private void newGame() {
        String choice = Dialog.getInstance().choicesDialog("Démarrer une nouvelle partie",
                "Veuillez sélectionner la langue",
                "Langue : ", "Français", "English");
        System.out.println(choice);
        // TODO demande de créer la nuvelle partie au serveur
    }


    @FXML
    private void goToGame() {
        // Partie de test static
        if (gamesPlayer.getSelectionModel().getSelectedItem().equals("Game Test Static")) {
            selectGame = gameTest.getGameDto();
            handleGotoGame();
        } else {
            // Partie venant du serveur
            Long selectId = gamesSummaryDto.get(gamesPlayer.getSelectionModel().getSelectedIndex()).getId();
            try {
                selectGame = GameApi.getGame(selectId);
                System.out.println(selectId);
                handleGotoGame();
            } catch (TokenNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
