package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.Api;
import ch.heigvd.wordoff.client.Api.MeApi;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.User.RelatedUserSummaryDto;
import ch.heigvd.wordoff.common.Dto.User.RelationDto;
import ch.heigvd.wordoff.common.Dto.User.RelationStatus;
import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * JavaFX controller for the friendly tournament settings window.
 */
public class FriendlyTournamentSettingsController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button addButton;

    @FXML
    private ListView<String> participantsList;

    @FXML
    private ListView<String> friendsList;

    @FXML
    private ListView<String> recentList;

    private LinkedList<String> participants = new LinkedList<>();

    EventHandler<MouseEvent> addToParticipantsHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            ListView lv = (ListView) event.getSource();
            if (event.getClickCount() == 2) {
                String s = (String) lv.getSelectionModel().getSelectedItem();
                if (!participantsList.getItems().contains(s)) {
                    participantsList.getItems().add(s);
                }
            }
        }
    };

    /**
     * Cancel tournament and close window
     * @param event Click event
     */
    @FXML
    void closeWindow(MouseEvent event) {
        cancel();
    }

    /**
     * Used to cancel tournament creation
     */
    private void cancel() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Called when initializing a JavaFX controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cancel();
            }
        });

        confirmButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                participants.addAll(participantsList.getItems());
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            }
        });

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (nameTextField.getText() != null && !nameTextField.getText().equals("")) {
                    participantsList.getItems().add(nameTextField.getText());
                    nameTextField.clear();
                }
            }
        });

        friendsList.setOnMouseClicked(addToParticipantsHandler);
        recentList.setOnMouseClicked(addToParticipantsHandler);
        participantsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    participantsList.getItems().remove(participantsList.getSelectionModel().getSelectedItem());
                }
            }
        });

        //initialize friend and recent opponent list
        try {
            MeDto meDto = MeApi.getCurrentUser();
            List<RelatedUserSummaryDto> rusDtos = Api.get(meDto.getRelations());
            List<UserSummaryDto> recentOpponents = Api.get(meDto.getAdversaries());
            for (RelatedUserSummaryDto r : rusDtos) {
                if (r.getRelation().getStatus().equals(RelationStatus.FRIEND)) {
                    friendsList.getItems().add(r.getName());
                }
            }
            recentList.getItems().addAll(recentOpponents.stream().map(p -> p.getName()).collect(Collectors.toList()));
        } catch (TokenNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns participants as String list
     * @return list of participants, empty if no other users participating
     */
    public List<String> getParticipants() {
        return participants;
    }


}
