package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.InvitationDto;
import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.NotificationDto;
import ch.heigvd.wordoff.common.Dto.User.CreateRelationDto;
import ch.heigvd.wordoff.common.Dto.User.RelatedUserSummaryDto;
import ch.heigvd.wordoff.common.Dto.User.RelationDto;
import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;
import ch.heigvd.wordoff.server.Model.Relation;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Service.ModeService;
import ch.heigvd.wordoff.server.Service.UserService;
import ch.heigvd.wordoff.server.Util.DtoFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller that allow for actions pertaining to the current user.
 */
@RestController
@RequestMapping(value = "/me", produces = "application/json")
public class MeController {
    private UserService userService;
    private ModeService modeService;

    public MeController(UserService userService, ModeService modeService) {
        this.userService = userService;
        this.modeService = modeService;
    }

    /**
     * GET information and endpoints about the given logged player.
     * @param player The current player.
     * @return The information and endpoints about the current logged user.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<MeDto> getMe(
            @RequestAttribute("player") User player) {
        return new ResponseEntity<>(DtoFactory.createMeFrom(player), HttpStatus.OK);
    }

    /**
     * GET the list of notifications pertaining to the given player.
     * @param player The current player.
     * @return The list of notifications.
     */
    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationDto>> getNotifications(
            @RequestAttribute("player") User player) {
        return new ResponseEntity<>(userService.getUserNotifications(player)
                .stream()
                .map(DtoFactory::createFrom)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * GET the list of invitations for the given player that have not yet been accepted or denied.
     * @param player The current player.
     * @return The list of invitations.
     */
    @RequestMapping(value = "/invitations", method = RequestMethod.GET)
    public ResponseEntity<List<InvitationDto>> getInvitations(
            @RequestAttribute("player") User player) {
        return new ResponseEntity<>(userService.getUserWaitingInvitations(player)
                .stream()
                .map(DtoFactory::createFrom)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Change the status of an invitation for a given player to a given mode.
     * @param player The current player.
     * @param modeId The id of the mode linked to the invitation.
     * @param invitation The invitation with the new values.
     * @return An empty response.
     */
    @RequestMapping(value = "/invitations/{modeId}", method = RequestMethod.PUT)
    public ResponseEntity putInvitation(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId,
            @RequestBody InvitationDto invitation) {

        modeService.changeInvitationStatus(modeId, player, invitation.getStatus());

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * GET the list of recent adversaries (players against which the given player has duelled).
     * @param player The current player.
     * @return The list of adversaries, each adversary given in a summarized form.
     */
    @RequestMapping(value = "/adversaries", method = RequestMethod.GET)
    public ResponseEntity<List<UserSummaryDto>> getAdversaries(
            @RequestAttribute("player") User player) {
        return new ResponseEntity<>(userService.getAdversaries(player)
                .stream()
                .map(DtoFactory::createSummaryFrom)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * GET the list of relations (FRIENDS and BLACKLISTED users) of the given player.
     * @param player The current player.
     * @return The list of relations, each relation given in a summarized form with data about the
     *      type of relation.
     */
    @RequestMapping(value = "/relations", method = RequestMethod.GET)
    public ResponseEntity<List<RelatedUserSummaryDto>> getRelations(
            @RequestAttribute("player") User player) {
        return new ResponseEntity<>(userService.getRelations(player)
                .stream()
                .map(DtoFactory::createRelatedSummaryFrom)
                .collect(Collectors.toList()), HttpStatus.OK);

    }

    /**
     * POST a new relation (FRIENDS and BLACKLISTED user) for the given player.
     * @param player The current player.
     * @param relation The information about the new relation
     * @return The summary with relation information of the new target of the relation.
     */
    @RequestMapping(value = "/relations", method = RequestMethod.POST)
    public ResponseEntity<RelatedUserSummaryDto> newRelation(
            @RequestAttribute("player") User player,
            @RequestBody CreateRelationDto relation) {

        User target = userService.getUser(relation.getTargetUsername());
        if(target == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Relation createdRelation = userService.setUsersRelation(player, target.getId(), relation.getStatus());

        return new ResponseEntity<>(DtoFactory.createRelatedSummaryFrom(createdRelation), HttpStatus.CREATED);
    }

    /**
     * Change the status of a relation between a given player and the target of the given relation.
     * @param player The current player.
     * @param targetId The id of the target of the relation.
     * @param relation The relation with the new status.
     * @return The updated relation.
     */
    @RequestMapping(value = "/relations/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<RelationDto> putRelation(
            @RequestAttribute("player") User player,
            @PathVariable("userId") Long targetId,
            @RequestBody RelationDto relation) {
        RelationDto dtoReturn = DtoFactory.createFrom(userService.setUsersRelation(player, targetId, relation.getStatus()));

        return new ResponseEntity<>(dtoReturn, HttpStatus.OK);
    }

}
