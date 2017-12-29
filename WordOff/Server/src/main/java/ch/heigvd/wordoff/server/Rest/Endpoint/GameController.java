package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.Game.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.GameSummaryDto;
import ch.heigvd.wordoff.common.Dto.Game.PowerDto;
import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
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

@RestController
@RequestMapping(value = "/games", produces = "application/json")
public class GameController {

    private GameRepository gameRepository;
    private LangSetRepository langSetRepository;
    private GameService gameService;
    private PlayerRepository playerRepository;

    public GameController(GameRepository gameRepository, GameService gameService, LangSetRepository langSetRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.langSetRepository = langSetRepository;
        this.playerRepository = playerRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GameSummaryDto>> listGames(
            @RequestAttribute("player") User player) {

        List<GameSummaryDto> gamesDto = gameRepository.retrieveAllByOnePlayerId(player.getId())
                .stream()
                .map(g -> DtoFactory.createSummaryFrom(g, player))
                .collect(Collectors.toCollection(ArrayList::new));

        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GameSummaryDto> newGame(
            @RequestAttribute("player") User player,
            @RequestParam("lang") String lang,
            @RequestBody List<Long> playersId) {

        if(playersId.size() != 2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LangSet langSet = langSetRepository.findByName(lang);
        if(langSet == null) {
            throw new ErrorCodeException(Protocol.LANG_NOT_EXISTS, "The language " + lang + " does not exist!");
        }

        Player init = playerRepository.findOne(playersId.get(0));
        Player resp = playerRepository.findOne(playersId.get(1));

        if(init == null || resp == null) {
            throw new ErrorCodeException(Protocol.PLAYER_NOT_EXISTS, "One of the player does not exists");
        }

        // create and convert the new games.
        Game game = gameService.initGame(init, resp, lang);
        GameSummaryDto gameSummaryDto = DtoFactory.createSummaryFrom(game, player);

        return new ResponseEntity<>(gameSummaryDto, HttpStatus.CREATED);
    }

    /**
     *
     * @param player
     * @param gameId
     * @return The challenge of the adversary
     */
    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<GameDto> getGame(
            @RequestAttribute("player") User player,
            @PathVariable("gameId") Long gameId) {

        if(!gameRepository.exists(gameId)) {
            throw new ErrorCodeException(Protocol.GAME_NOT_EXISTS, "The specified game does not exists!");
        }

        Game game = gameRepository.findOne(gameId);
        if(!game.getCurrPlayer().getId().equals(player.getId()) && !game.getOtherPlayer(game.getCurrPlayer()).getId().equals(player.getId())) {
            throw new ErrorCodeException(Protocol.NOT_PLAYER_GAME, "The game does not belong to you!");
        }
//
//        if (game.getCurrPlayer() instanceof Ai) {
//            game = gameService.makeAiPLay(game, (User) player);
//        }

        // Converter new game to dto
        GameDto gameDto = DtoFactory.createFrom(game, player);

        // send new game to player
        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }

    /**
     *
     * @param player The player who send the request
     * @param gameId The gameId
     * @param challengeDto the challenge with the word choosed by the player
     * @return The side of the player that is change
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

    @RequestMapping(value = "/{gameId}/powers", method = RequestMethod.POST, consumes = "application/json")
    public void hint(@RequestAttribute("player") User player,
                               @PathVariable("gameId") Long gameId,
                               @RequestBody PowerDto powerDto) {

        if(powerDto.equals(PowerDto.HINT)) {
            // HINT
        } else if (powerDto.equals(PowerDto.PASS)) {
            gameService.pass(gameRepository.findOne(gameId), player);
        } else if (powerDto.equals(PowerDto.PEEK)) {
            // PEEK
        } else if (powerDto.equals(PowerDto.DISCARD_2)) {
            // DISCARD 2
        } else if (powerDto.equals(PowerDto.DISCARD_ALL)) {
            // DISCARD ALL
        } else if (powerDto.equals(PowerDto.WORDANALYZER)) {
            // WORDANALYZER
        } else {
            throw new ErrorCodeException(Protocol.CHEATING, "Requested power does not exist");
        }
    }
}
