package ch.heigvd.wordoff.server.Rest.Endpoint;(fetch = FetchType.EAGER)

import ch.heigvd.wordoff.common.Dto.MessageDto;
import ch.heigvd.wordoff.common.Dto.Mode.CreateModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeSummaryDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.ModeRepository;
import ch.heigvd.wordoff.server.Service.ModeService;
import ch.heigvd.wordoff.server.Util.DtoFactory;
import ch.heigvd.wordoff.server.Util.EntityFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/modes", produces = "application/json")
public class ModeController {
    private ModeService modeService;
    private ModeRepository modeRepository;

    public ModeController(ModeService modeService, ModeRepository modeRepository) {
        this.modeService = modeService;
        this.modeRepository = modeRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ModeSummaryDto>> getModes(
            @RequestAttribute("player") User player) {
            List<ModeSummaryDto> modesDTO = modeRepository.findAllByPlayerId(player.getId())
                    .stream()
                    .map(m -> DtoFactory.createSummaryFrom(m, player))
                    .collect(Collectors.toCollection(ArrayList::new));

        return new ResponseEntity<>(modesDTO, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ModeSummaryDto> newMode(
            @RequestAttribute("player") User player,
            @RequestBody CreateModeDto modeDto) {


        Mode mode = modeService.initMode(player, modeDto.getName(), modeDto.getParticipants(), modeDto.getType());
        ModeSummaryDto modeSummaryDto = DtoFactory.createFrom(mode);

        return new ResponseEntity<>(modeSummaryDto, HttpStatus.CREATED);


    }

    @RequestMapping(value = "/{modeId}",method = RequestMethod.GET)
    public ResponseEntity<ModeDto> getModeId(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId) {

    }

    @RequestMapping(value = "/{modeId}/messages",method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessages(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId) {

    }

    @RequestMapping(value = "/{modeId}/messages",method = RequestMethod.POST)
    public ResponseEntity<MessageDto> postMessages(
            @RequestAttribute("player") User player,
            @PathVariable("modeId") Long modeId,
            @RequestBody MessageDto message) {

    }
}
