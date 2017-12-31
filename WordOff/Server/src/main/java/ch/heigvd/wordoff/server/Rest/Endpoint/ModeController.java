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
     * Method called by the client when he wants to get the list of all the modes associated to him
     * @param player The user
     * @return The list of mode associated to the user
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
     * Method called by the client when he wants to create a new mode or join a mode
     * @param player The user
     * @param modeDto The mode he wants to create
     * @return the new mode or a mode that he will join
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ModeSummaryDto> newMode(
            @RequestAttribute("player") User player,
            @RequestBody CreateModeDto modeDto) {
        Mode mode = modeService.initMode(player, modeDto.getName(), modeDto.getParticipants(), modeDto.getType(), modeDto.getLang());

        ModeSummaryDto modeSummaryDto = DtoFactory.createSummaryFrom(mode, player);

        // TODO -> discussion, le mode n'est pas tjrs créer, parfois ou plutôt souvent, l'utilisateur rejoindra un mode. Laissons-nous le status CREATED ?
        return new ResponseEntity<>(modeSummaryDto, HttpStatus.CREATED);
    }

    /**
     *
     * @param player
     * @param modeId
     * @return
     */
    @RequestMapping(value = "/{modeId}",method = RequestMethod.GET)
    public ResponseEntity<ModeDto> getMode(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId) {
        ModeDto modeDto = DtoFactory.createFrom(modeService.getMode(modeId), player);

        return new ResponseEntity<>(modeDto, HttpStatus.OK);
    }

    /**
     * Create a new game (to be used only in Tournament mode when the player wants to play a day with a ticket)
     * @param player The user
     * @param modeId The id of the mode
     * @return a HTTP response with the game as a DTO
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

    @RequestMapping(value = "/{modeId}/messages",method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessages(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId) {
        List<MessageDto> messages = new ArrayList<>();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @RequestMapping(value = "/{modeId}/messages",method = RequestMethod.POST)
    public ResponseEntity<MessageDto> postMessages(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId,
            @RequestBody MessageDto message) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
