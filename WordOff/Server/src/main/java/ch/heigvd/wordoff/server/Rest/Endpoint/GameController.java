package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Service.GameService;
import ch.heigvd.wordoff.common.Model.ChallengeDto;
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
    @RequestMapping(value = "/play", method = RequestMethod.POST)
    public ResponseEntity<Game> play(@RequestAttribute("player") Player player, @PathVariable("gameId") Long gameId, @RequestBody ChallengeDto challengeDto) {
        Game game = gameRepository.findOne(gameId);

        return gameService.play(game, player, challengeDto);
    }

    /**
     *
     * @param player
     * @param gameId
     * @return The challenge of the adversary
     */
    @RequestMapping(value = "/getGame", method = RequestMethod.GET)
    public ResponseEntity<Game> getGame(@RequestAttribute("player") Player player, @PathVariable("gameId") Long gameId) {
        // TODO : cette méthode renvera la version DTO de Game qui ne contient pas le rack de l'adversaire
        Game game = gameRepository.findOne(gameId);

        if (game.getCurrPlayer() instanceof Ai) {
            return gameService.makeAiPLay(game, (User) player);
        } else {
            // send message to player that it's not his turn */
            return new ResponseEntity<Game>(game, HttpStatus.I_AM_A_TEAPOT);
        }
    }


}
