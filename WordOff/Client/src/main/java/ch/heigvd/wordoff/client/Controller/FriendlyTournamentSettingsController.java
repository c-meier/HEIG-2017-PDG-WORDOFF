package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.Api;
import ch.heigvd.wordoff.client.Api.MeApi;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.User.RelatedUserSummaryDto;
import ch.heigvd.wordoff.common.Dto.User.RelationDto;
import ch.heigvd.wordoff.common.Dto.User.RelationStatus;
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
            ListView lv = (ListView)event.getSource();
            if(event.getClickCount() == 2){
                String s = (String)lv.getSelectionModel().getSelectedItem();
                if(!participantsList.getItems().contains(s)){
                    participantsList.getItems().add(s);
                }
            }
        }
    };

    @FXML
    void closeWindow(MouseEvent event) {
        cancel();
    }

    private void cancel() {
        Stage stage = (Stage)confirmButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                cancel();
            }
        });

        confirmButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                participants.addAll(participantsList.getItems());
                Stage stage = (Stage)confirmButton.getScene().getWindow();
                stage.close();
            }
        });

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(nameTextField.getText() != null && !nameTextField.getText().equals("")){
                    participantsList.getItems().add(nameTextField.getText());
                    nameTextField.clear();
                }
            }
        });

        friendsList.setOnMouseClicked(addToParticipantsHandler);
        recentList.setOnMouseClicked(addToParticipantsHandler);

        //initialize friend and recent opponent list
        try {
            MeDto meDto = MeApi.getCurrentUser();
            List<RelatedUserSummaryDto> rusDtos = Api.get(meDto.getRelations());
            for(RelatedUserSummaryDto r : rusDtos){
                if(r.getRelation().getStatus().equals(RelationStatus.FRIEND)){
                    friendsList.getItems().add(r.getName());
                }
            }
        } catch (TokenNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getParticipants(){
        return participants;
    }


}
