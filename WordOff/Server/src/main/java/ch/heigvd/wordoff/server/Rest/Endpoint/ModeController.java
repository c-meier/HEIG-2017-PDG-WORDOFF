package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.MessageDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeSummaryDto;
import ch.heigvd.wordoff.server.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/modes", produces = "application/json")
public class ModeController {
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ModeSummaryDto>> getModes(
            @RequestAttribute("player") User player) {

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ModeSummaryDto> postModes(
            @RequestAttribute("player") User player,
            @RequestBody ModeSummaryDto modeSummary) {

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
