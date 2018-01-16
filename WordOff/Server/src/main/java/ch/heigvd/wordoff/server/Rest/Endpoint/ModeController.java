/*
 * File: ModeController.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.common.Dto.MessageDto;
import ch.heigvd.wordoff.common.Dto.Mode.CreateModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeSummaryDto;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.InvitationRepository;
import ch.heigvd.wordoff.server.Service.ModeService;
import ch.heigvd.wordoff.server.Util.DtoFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller to act upon modes.
 */
@RestController
@RequestMapping(value = "/modes", produces = "application/json")
public class ModeController {
    private ModeService modeService;
    private InvitationRepository invitationRepository;

    public ModeController(ModeService modeService, InvitationRepository invitationRepository) {
        this.modeService = modeService;
        this.invitationRepository = invitationRepository;
    }

    /**
     * Method called by the client when he wants to get the list of all the modes associated with him.
     * @param player The current user.
     * @return The list of mode associated with the user.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ModeSummaryDto>> getModes(
            @RequestAttribute("player") User player) {
        List<ModeSummaryDto> modesDTO = Stream.of(invitationRepository.findAllByPkTargetAndStatus(player, InvitationStatus.ACCEPT),
            invitationRepository.findAllByPkTargetAndStatus(player, InvitationStatus.ORIGIN))
                .flatMap(Collection::stream)
                .map(Invitation::getMode)
                .map(m -> DtoFactory.createSummaryFrom(m, player))
                .collect(Collectors.toList());

        return new ResponseEntity<>(modesDTO, HttpStatus.OK);
    }

    /**
     * Method called by the client when he wants to create a new mode or join a mode.
     * @param player The current user.
     * @param modeDto The information about the mode he wants to create.
     * @return The new mode or a mode that he will join.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ModeSummaryDto> newMode(
            @RequestAttribute("player") User player,
            @RequestBody CreateModeDto modeDto) {
        Mode mode = modeService.initMode(player, modeDto.getName(), modeDto.getParticipants(), modeDto.getType(), modeDto.getLang());

        ModeSummaryDto modeSummaryDto = DtoFactory.createSummaryFrom(mode, player);

        return new ResponseEntity<>(modeSummaryDto, HttpStatus.CREATED);
    }

    /**
     * GET the mode associated with the given id.
     * @param player The current user.
     * @param modeId The id of the mode.
     * @return The mode associated with the given id.
     */
    @RequestMapping(value = "/{modeId}",method = RequestMethod.GET)
    public ResponseEntity<ModeDto> getMode(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId) {
        Mode mode = modeService.getMode(modeId);
        if(mode == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ModeDto modeDto = DtoFactory.createFrom(mode, player);

            return new ResponseEntity<>(modeDto, HttpStatus.OK);
        }
    }

    /**
     * Create a new game (to be used only in Tournament mode when the player wants to play a day with a ticket).
     * @param player The current user.
     * @param modeId The id of the mode.
     * @return a HTTP response with the game as a DTO.
     */
    @RequestMapping(value = "/{modeId}/games", method = RequestMethod.POST)
    public ResponseEntity<GameDto> getNewGame(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId) {

        // Create the new game
        Game game = modeService.initModeGame(modeId, player);
        
        // Convert the new game in dto
        GameDto gameDto = DtoFactory.createFrom(game, player);

        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }

    /**
     * GET the list of messages associated with the given mode id.
     * @param player The current user.
     * @param modeId The id of the mode.
     * @return The list of messages.
     */
    @RequestMapping(value = "/{modeId}/messages",method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessages(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId) {
        List<MessageDto> messages = new ArrayList<>();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /**
     * POST a new message for the given mode id.
     * @param player The current user.
     * @param modeId The id of the mode.
     * @param message The message to be posted.
     * @return The message posted.
     */
    @RequestMapping(value = "/{modeId}/messages",method = RequestMethod.POST)
    public ResponseEntity<MessageDto> postMessages(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId,
            @RequestBody MessageDto message) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
