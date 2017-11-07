package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.GameDto;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Service.GameService;
import ch.heigvd.wordoff.common.Dto.ChallengeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/game/{gameId}", produces = "application/json")
public class GameController {
    private GameRepository gameRepository;
    private GameService gameService;

    public GameController(GameRepository gameRepository, GameService gameService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    /**
     *
     * @param player The player who send the request
     * @param gameId The gameId
     * @param challengeDto the challenge with the word choosed by the player
     * @return The side of the player that is change
     */
    @RequestMapping(value = "/challenge", method = RequestMethod.POST)
    public ResponseEntity<GameDto> play(@RequestAttribute("player") Player player, @PathVariable("gameId") Long gameId, @RequestBody ChallengeDto challengeDto) {
        Game game = gameRepository.findOne(gameId);

        Challenge challenge = null; // TODO -> replace with converter

        game = gameService.play(game, player, challenge);

        GameDto gameDto = null; // TODO -> replace with converter

        return new ResponseEntity<GameDto>(gameDto, HttpStatus.OK);
    }

    /**
     *
     * @param player
     * @param gameId
     * @return The challenge of the adversary
     */
    @RequestMapping(value = "/getGame", method = RequestMethod.GET)
    public ResponseEntity<GameDto> getGame(@RequestAttribute("player") Player player, @PathVariable("gameId") Long gameId) {
        Game game = gameRepository.findOne(gameId);

        if (game.getCurrPlayer() instanceof Ai) {
            game = gameService.makeAiPLay(game, (User) player);
        }

        GameDto gameDto = null; // TODO -> replace with converter

        return new ResponseEntity<GameDto>(gameDto, HttpStatus.OK);
    }
}
