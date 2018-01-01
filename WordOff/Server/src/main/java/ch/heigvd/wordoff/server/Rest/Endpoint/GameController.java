package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.Game.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.GameSummaryDto;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Ai;
import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import ch.heigvd.wordoff.server.Service.GameService;
import ch.heigvd.wordoff.server.Util.DtoFactory;
import ch.heigvd.wordoff.server.Util.EntityFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to act upon games.
 */
@RestController
@RequestMapping(value = "/games", produces = "application/json")
public class GameController {
    private GameRepository gameRepository;
    private GameService gameService;

    public GameController(GameRepository gameRepository, GameService gameService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

    /**
     * GET the list of game of a given player.
     * @deprecated
     * @param player The current player.
     * @return The list of game, each as a summary.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GameSummaryDto>> listGames(
            @RequestAttribute("player") User player) {

        List<GameSummaryDto> gamesDto = gameRepository.retrieveAllByOnePlayerId(player.getId())
                .stream()
                .map(g -> DtoFactory.createSummaryFrom(g, player))
                .collect(Collectors.toCollection(ArrayList::new));

        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

    /**
     * GET the information about the game specified by the given id.
     * @param player The current player.
     * @param gameId The game id.
     * @return The game linked to the id.
     */
    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<GameDto> getGame(
            @RequestAttribute("player") User player,
            @PathVariable("gameId") Long gameId) {
        Game game = gameRepository.findOne(gameId);

        if(game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!game.concernPlayer(player)) {
            throw new ErrorCodeException(Protocol.NOT_PLAYER_GAME, "The game does not concern you!");
        }

        if (game.getCurrPlayer() instanceof Ai) {
            game = gameService.makeAiPLay(game, player);
        }

        // Converter new game to dto
        GameDto gameDto = DtoFactory.createFrom(game, player);

        // send new game to player
        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }

    /**
     * Play a word in response to a challenge.
     * @param player The player who send the request.
     * @param gameId The gameId.
     * @param challengeDto the challenge with the word chosen by the player.
     * @return The game that is changed.
     */
    @RequestMapping(value = "/{gameId}/challenge", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<GameDto> play(
            @RequestAttribute("player") User player,
            @PathVariable("gameId") Long gameId,
            @RequestBody ChallengeDto challengeDto) {

        if(!challengeDto.isWellformed()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Game game = gameRepository.findOne(gameId);

        Challenge challenge = EntityFactory.createFrom(challengeDto);

        game = gameService.play(game, player, challenge);

        // convert dao game to dto
        GameDto gameDto = DtoFactory.createFrom(game, player);

        // Send update of the game
        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }
}
