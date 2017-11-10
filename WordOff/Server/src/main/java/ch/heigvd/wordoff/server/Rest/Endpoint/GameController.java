package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.*;
import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Slots.*;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import ch.heigvd.wordoff.server.Service.GameService;
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

    public GameController(GameRepository gameRepository, GameService gameService, LangSetRepository langSetRepository) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.langSetRepository = langSetRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GameSummaryDto>> listGames(
            @RequestAttribute("player") Player player) {

        // TODO -> recup and convert the player games.
        List<GameSummaryDto> games = new ArrayList<>();

        games.add(new GameSummaryDto(1L, new PlayerDto(1L, "AI")));
        games.add(new GameSummaryDto(2L, new PlayerDto(1L, "One")));

        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GameSummaryDto> newGame(
//            @RequestAttribute("player") Player player,
            @RequestParam("lang") String lang,
            @RequestBody List<Long> playersId) {

        if(playersId.size() != 2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LangSet langSet = langSetRepository.findByName(lang);
        if(langSet == null) {
            throw new ErrorCodeException(Protocol.LANG_NOT_EXISTS, "The language " + lang + " does not exist!");
        }

        // TODO -> create and convert the new games.
        GameSummaryDto game = new GameSummaryDto(1L, new PlayerDto(playersId.get(0), "AI"));

        return new ResponseEntity<>(game, HttpStatus.CREATED);
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

//        Game game = gameRepository.findOne(gameId);
//
//        if (game.getCurrPlayer() instanceof Ai) {
//            game = gameService.makeAiPLay(game, (User) player);
//        }

        // TODO -> replace with converter
        ChallengeDto challengeDto = new ChallengeDto(Arrays.asList(
                new SlotDto(1L, (short)1),
                new SlotDto(1L, (short)2),
                new SwapSlotDto(1L, (short)3),
                new L2SlotDto(1L, (short)4),
                new SwapSlotDto(1L, (short)5),
                new SlotDto(1L, (short)6),
                new LastSlotDto(1L, (short)7)),
                new SwapRackDto(Arrays.asList(new TileDto(7, 'B', 1))));
        challengeDto.addTile(new TileDto(2, 'X', 0));
        challengeDto.addTile(new TileDto(13, 'E', 1));

        GameDto gameDto = new GameDto(1L,
                new SideDto(1L,
                        new PlayerDto(player.getId(), player.getName()),
                        new ChallengeDto(Arrays.asList(
                                new L2SlotDto(1L, (short)1),
                                new SwapSlotDto(1L, (short)2),
                                new SlotDto(1L, (short)3),
                                new L3SlotDto(1L, (short)4),
                                new SwapSlotDto(1L, (short)5),
                                new SlotDto(1L, (short)6),
                                new LastSlotDto(1L, (short)7)),
                                new SwapRackDto(Arrays.asList(
                                        new TileDto(1, '#', 0),
                                        new TileDto(3, 'A', 1)))),
                        new PlayerRackDto(Arrays.asList(
                                new TileDto(1, '#', 0),
                                new TileDto(24, 'L', 3),
                                new TileDto(12, 'E', 1),
                                new TileDto(21, 'K', 8),
                                new TileDto(11, 'E', 1),
                                new TileDto(5, 'A', 1),
                                new TileDto(8, 'B', 4))),
                        0
                ),
                new OtherSideDto(2L,
                        new PlayerDto(1L, "AI"),
                        challengeDto,
                        0),
                true,
                "fr",
                new Date()
        );

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

//        Game game = gameRepository.findOne(gameId);

        Challenge challenge = null; // TODO -> replace with converter

//        game = gameService.play(game, player, challenge);

        // TODO -> replace with converter
        ChallengeDto challengeDtoRet = new ChallengeDto(Arrays.asList(
                new SlotDto(1L, (short)1),
                new SlotDto(1L, (short)2),
                new SwapSlotDto(1L, (short)3),
                new L2SlotDto(1L, (short)4),
                new SwapSlotDto(1L, (short)5),
                new SlotDto(1L, (short)6),
                new LastSlotDto(1L, (short)7)),
                new SwapRackDto(Arrays.asList(new TileDto(76, 'O', 1))));

        GameDto gameDto = new GameDto(1L,
                new SideDto(1L,
                        new PlayerDto(player.getId(), player.getName()),
                        new ChallengeDto(Arrays.asList(
                                new L2SlotDto(1L, (short)1),
                                new SwapSlotDto(1L, (short)2),
                                new SlotDto(1L, (short)3),
                                new L3SlotDto(1L, (short)4),
                                new SwapSlotDto(1L, (short)5),
                                new SlotDto(1L, (short)6),
                                new LastSlotDto(1L, (short)7)),
                                new SwapRackDto(Arrays.asList(
                                        new TileDto(1, '#', 0),
                                        new TileDto(3, 'A', 1)))),
                        new PlayerRackDto(Arrays.asList(
                                new TileDto(1, '#', 0),
                                new TileDto(24, 'L', 3),
                                new TileDto(12, 'E', 1),
                                new TileDto(21, 'K', 8),
                                new TileDto(11, 'E', 1),
                                new TileDto(5, 'A', 1),
                                new TileDto(8, 'B', 4))),
                        0
                ),
                new OtherSideDto(2L,
                        new PlayerDto(1L, "AI"),
                        challengeDto,
                        0),
                false,
                "fr",
                new Date()
        );

        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }
}
