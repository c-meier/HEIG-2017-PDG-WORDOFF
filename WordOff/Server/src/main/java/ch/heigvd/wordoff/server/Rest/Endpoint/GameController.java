package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.*;
import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Slots.*;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import ch.heigvd.wordoff.server.Service.GameService;
import ch.heigvd.wordoff.server.Util.DaoDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/games", produces = "application/json")
public class GameController {
    private GameRepository gameRepository;
    private LangSetRepository langSetRepository;
    private GameService gameService;
    private DaoDtoConverter daoDtoConverter;
    private PlayerRepository playerRepository;

    public GameController(GameRepository gameRepository, GameService gameService, LangSetRepository langSetRepository, DaoDtoConverter daoDtoConverter, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.langSetRepository = langSetRepository;
        this.daoDtoConverter = daoDtoConverter;
        this.playerRepository = playerRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GameSummaryDto>> listGames(
            @RequestAttribute("player") Player player) {

        List<GameSummaryDto> gamesDto = new ArrayList<>();

        List<Game> games = gameRepository.findAllBySideInitPlayerId(player.getId());
        for(Game g : games) {
            gamesDto.add(daoDtoConverter.toSummaryDto(g, player));
        }
        games = gameRepository.findAllBySideRespPlayerId(player.getId());
        for(Game g : games) {
            gamesDto.add(daoDtoConverter.toSummaryDto(g, player));
        }

        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GameSummaryDto> newGame(
            @RequestAttribute("player") Player player,
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
        GameSummaryDto gameSummaryDto = daoDtoConverter.toSummaryDto(game, player);

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
            @RequestAttribute("player") Player player,
            @PathVariable("gameId") Long gameId) {
        boolean gameExists = true;
        if(!gameExists) {
            throw new ErrorCodeException(Protocol.GAME_NOT_EXISTS, "The specified game does not exists!");
        }

        boolean gameConcernPlayer = true;
        if(!gameConcernPlayer) {
            throw new ErrorCodeException(Protocol.NOT_PLAYER_GAME, "The game does not belong to you!");
        }

        Game game = gameRepository.findOne(gameId);
//
//        if (game.getCurrPlayer() instanceof Ai) {
//            game = gameService.makeAiPLay(game, (User) player);
//        }

        // Converter new game to dto
        GameDto gameDto = daoDtoConverter.toDto(game, player);

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
            @RequestAttribute("player") Player player,
            @PathVariable("gameId") Long gameId,
            @RequestBody ChallengeDto challengeDto) {

        if(!challengeDto.isWellformed()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Game game = gameRepository.findOne(gameId);

        Challenge challenge = daoDtoConverter.fromDto(challengeDto);

        game = gameService.play(game, player, challenge);
        // convert dao game to dto
        GameDto gameDto = daoDtoConverter.toDto(game, player);

        // Send update of the game
        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }
}
