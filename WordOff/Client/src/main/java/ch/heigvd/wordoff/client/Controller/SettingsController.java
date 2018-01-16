/*
 * File: SettingsController.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.Api;
import ch.heigvd.wordoff.client.Api.MeApi;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.client.Util.UtilChangeScene;
import ch.heigvd.wordoff.client.Util.UtilStringReference;
import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.User.CreateRelationDto;
import ch.heigvd.wordoff.common.Dto.User.RelatedUserSummaryDto;
import ch.heigvd.wordoff.common.Dto.User.RelationStatus;
import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import javax.xml.ws.http.HTTPException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the settings screen
 */
public class SettingsController implements Initializable {

    @FXML
    private CheckBox checkBoxSound;
    @FXML
    private CheckBox checkBoxNotifications;
    @FXML
    private CheckBox checkBoxHelp;
    @FXML
    private ChoiceBox<String> choiceBoxLang;
    @FXML
    private Label playerName;
    @FXML
    private ImageView playerAvatar;
    @FXML
    private Label coinLabel;
    @FXML
    private ListView<String> friendsList;
    @FXML
    private ListView<String> blackList;
    private MeDto meDto;
    private UserSummaryDto user;
    private List<RelatedUserSummaryDto> relatedUsers;

    @FXML
    private void handleGotoMenu(ActionEvent event) {
        UtilChangeScene.getInstance().handleGotoMenu();
    }

    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initState();
    }

    private void initState() {
        try {
            meDto = MeApi.getCurrentUser();
            relatedUsers = Api.get(meDto.getRelations());
            user = meDto.getSelf();
        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalError("Impossible de récupérer les données du profil");
        }
        checkBoxSound.setText(UtilStringReference.TEXT_PARAM_SOUND);
        checkBoxHelp.setText(UtilStringReference.TEXT_PARAM_HELP);
        checkBoxNotifications.setText(UtilStringReference.TEXT_PARAM_NOTIF);
        choiceBoxLang.getItems().addAll(UtilStringReference.TEXT_PARAM_LANG);
        choiceBoxLang.setValue(UtilStringReference.TEXT_PARAM_LANG.get(0));
        playerName.setText(user.getName());
        coinLabel.setText(String.valueOf(meDto.getCoins()));
        for (RelatedUserSummaryDto rusDto : relatedUsers) {
            switch (rusDto.getRelation().getStatus()) {
                case FRIEND:
                    friendsList.getItems().add(rusDto.getName());
                    break;
                case BLOCKED:
                    blackList.getItems().add(rusDto.getName());
                    break;

            }
        }
    }

    @FXML
    private void changeAvatar() {
        // pas implémenté

    }

    @FXML
    private void addNewFriend() {
        String friend = null;
        friend = Dialog.getInstance().choiceNameOpponent("Entrez le nom de votre ami");
        if (friend != null && !friendsList.getItems().contains(friend)) {
            CreateRelationDto newRelation = new CreateRelationDto(friend, RelationStatus.FRIEND);
            try {
                RelatedUserSummaryDto rusDto = Api.post(meDto.getRelations(), newRelation);
                friendsList.getItems().add(rusDto.getName());
                blackList.getItems().remove(friend);
            } catch (TokenNotFoundException e) {
                Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
            } catch (HTTPException e) {
                //Test to see if correct exception is thrown when user does not exist
                Dialog.getInstance().signalError(UtilStringReference.PLAYER_NOT_FOUND);
            }
        }


    }

    @FXML
    private void addBlackList() {
        String friend = null;
        friend = Dialog.getInstance().choiceNameOpponent("Entrez le nom du joueur à bloquer");
        if (friend != null && !blackList.getItems().contains(friend)) {
            CreateRelationDto newRelation = new CreateRelationDto(friend, RelationStatus.BLOCKED);
            try {
                RelatedUserSummaryDto rusDto = Api.post(meDto.getRelations(), newRelation);
                blackList.getItems().add(rusDto.getName());
                friendsList.getItems().remove(friend);
            } catch (TokenNotFoundException e) {
                Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
            } catch (HTTPException e) {
                Dialog.getInstance().signalError(UtilStringReference.PLAYER_NOT_FOUND);
            }
        }
    }

    @FXML
    private void removeFriend() {
        String selectedFriend = friendsList.getSelectionModel().getSelectedItem();
        if (!(selectedFriend == null)) {
            CreateRelationDto newRelation = new CreateRelationDto(selectedFriend, RelationStatus.NONE);
            try {
                RelatedUserSummaryDto rusDto = Api.post(meDto.getRelations(), newRelation);
                friendsList.getItems().remove(selectedFriend);
            } catch (TokenNotFoundException e) {
                Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
            } catch (HTTPException e) {
                Dialog.getInstance().signalError(UtilStringReference.PLAYER_NOT_FOUND);
            }
        }
    }

    @FXML
    private void removeBlackList() {
        String selectedFriend = blackList.getSelectionModel().getSelectedItem();
        if (!(selectedFriend == null)) {
            CreateRelationDto newRelation = new CreateRelationDto(selectedFriend, RelationStatus.NONE);
            try {
                RelatedUserSummaryDto rusDto = Api.post(meDto.getRelations(), newRelation);
                blackList.getItems().remove(selectedFriend);
            } catch (TokenNotFoundException e) {
                Dialog.getInstance().signalError(UtilStringReference.ERROR_TOKEN);
            } catch (HTTPException e) {
                Dialog.getInstance().signalError(UtilStringReference.PLAYER_NOT_FOUND);
            }
        }
    }
}
