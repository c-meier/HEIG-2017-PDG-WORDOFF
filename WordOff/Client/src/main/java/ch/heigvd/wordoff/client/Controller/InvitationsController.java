package ch.heigvd.wordoff.client.Controller;

import ch.heigvd.wordoff.client.Api.Api;
import ch.heigvd.wordoff.client.Api.MeApi;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Util.Dialog;
import ch.heigvd.wordoff.client.Util.UtilChangeScene;
import ch.heigvd.wordoff.common.Dto.InvitationDto;
import ch.heigvd.wordoff.common.Dto.MeDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Gabriel Luthier
 */
public class InvitationsController implements Initializable {

    @FXML
    ListView<InvitationDto> listNewInvitation;
    @FXML
    ListView<InvitationDto> listAcceptInvitation;
    @FXML
    ListView<InvitationDto> listRefusedInvitation;

    private MeDto meDto;
    private List<InvitationDto> invitations = new LinkedList<>();



    @FXML
    private void handleGotoMenu(ActionEvent event) {
        UtilChangeScene.getInstance().handleGotoMenu();
    }

    /**
     * Initializes the Controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            meDto = MeApi.getCurrentUser();
            invitations = Api.get(meDto.getInvitations());

        } catch (TokenNotFoundException e) {
            Dialog.getInstance().signalError("Impossible de récupérer les données du profil");
        }

        listNewInvitation.setCellFactory(param -> new ListCell<InvitationDto>(){
            @Override
            public void updateItem(InvitationDto iDto, boolean empty){
                super.updateItem(iDto, empty);
                if(empty){
                    setText(null);
                    setGraphic(null);
                } else {
                    setText("Invitation : " + iDto.getName());
                    setGraphic(null);
                }
            }

        });

        for(InvitationDto iDto : invitations){
            switch(iDto.getStatus()){
                case ACCEPT:
                    listAcceptInvitation.getItems().add(iDto);
                case DENY:
                    listRefusedInvitation.getItems().add(iDto);
                case WAITING:
                    listNewInvitation.getItems().add(iDto);
                case ORIGIN:
            }
        }

    }    
    
}
