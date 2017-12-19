package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.InvitationDto;
import ch.heigvd.wordoff.common.Dto.NotificationDto;
import ch.heigvd.wordoff.common.Dto.User.MeDto;
import ch.heigvd.wordoff.common.Dto.User.RelatedUserSummaryDto;
import ch.heigvd.wordoff.common.Dto.User.RelationDto;
import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Util.DtoFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/me", produces = "application/json")
public class MeController {
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<MeDto> getMe(
            @RequestAttribute("player") User player) {
        return new ResponseEntity<>(DtoFactory.createMeFrom(player), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<MeDto> putMe(
            @RequestAttribute("player") User player,
            @RequestBody MeDto me) {

        if(false) {
            // The new name or avatar can not be updated.

            // TODO: integrate with service

            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(DtoFactory.createMeFrom(player), HttpStatus.OK);
    }

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationDto>> getNotifications(
            @RequestAttribute("player") User player) {
        List<NotificationDto> notifications = new ArrayList<>();

        // TODO: integrate with service
        // TODO: what are notifications ???

        NotificationDto notif = new NotificationDto();
        notifications.add(notif);

        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @RequestMapping(value = "/invitations", method = RequestMethod.GET)
    public ResponseEntity<List<InvitationDto>> getInvitations(
            @RequestAttribute("player") User player) {

    }

    @RequestMapping(value = "/invitations/{modeId}", method = RequestMethod.PUT)
    public ResponseEntity<InvitationDto> putInvitation(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId,
            @RequestBody InvitationDto invitation) {

    }

    @RequestMapping(value = "/adversaries", method = RequestMethod.GET)
    public ResponseEntity<List<UserSummaryDto>> getAdversaries(
            @RequestAttribute("player") User player) {

    }

    @RequestMapping(value = "/relations", method = RequestMethod.GET)
    public ResponseEntity<List<RelatedUserSummaryDto>> getRelations(
            @RequestAttribute("player") User player) {

    }

    @RequestMapping(value = "/relations/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<RelationDto> putRelation(
            @RequestAttribute("player") User player,
            @PathVariable("userId") Long userId,
            @RequestBody RelationDto relation) {

    }

}
