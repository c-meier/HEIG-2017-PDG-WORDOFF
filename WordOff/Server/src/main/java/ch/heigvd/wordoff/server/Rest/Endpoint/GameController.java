package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Model.Tiles.TileDto;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Util.DictionaryLoader;
import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Model.ChallengeDto;
import ch.heigvd.wordoff.common.Model.SideDto;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.common.WordAnalyzer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/game/{gameId}", produces = "application/json")
public class GameController {
    private GameRepository gameRepository;
    private DictionaryLoader dictionaryLoader;

    public GameController(GameRepository gameRepository, DictionaryLoader dictionaryLoader) {
        this.gameRepository = gameRepository;
        this.dictionaryLoader = dictionaryLoader;
    }

    /**
     *
     * @param player The player who send the request
     * @param gameId The gameId
     * @param challengeDto the challenge with the word choosed by the player
     * @return The side of the player that is change
     */
    @RequestMapping(value = "/play", method = RequestMethod.POST)
    public SideDto play(@RequestAttribute("player") Player player, @PathVariable("gameId") Long gameId, @RequestBody ChallengeDto challengeDto) {
        Game game = gameRepository.findOne(gameId);

        Challenge challenge = null; // TODO -> replace with converter

        // Load the dico
        dictionaryLoader.loadDictionary(game.getLang());

        Side side = null;

        // Check if it's the right player who try to play
        if (player.equals(game.getCurrPlayer())) {
            String wordChallenge = challenge.getWord();

            // If the word doesn't exists
            if (!dictionaryLoader.getDico(game.getLang()).contains(wordChallenge)) {
                /* TODO -> Return a message to the side */
            } else {
                // get a list of the tile taken from the bag of the game
                List<Tile> newTiles = game.getBag().getXTile(Constants.PLAYER_RACK_SIZE -
                        game.getSideOfPlayer(player).getPlayerRack().getRack().size());

                // Update the player side
                updateSide(game.getSideOfPlayer(player), challenge, newTiles);

                // switch player
                game.setCurrPlayer(game.getOtherPlayer(player));

                /* TODO -> Send swap tiles to other player */

                // PlayerDto Side updated
                side = game.getSideOfPlayer(player);
            }
        } else {
            // return the Side of the player 1 if it wasn't his turn to play
            if (game.getSideOfPlayer(player) == game.getSideInit()) {
                side = game.getSideInit();
            } else if (game.getSideOfPlayer(player) == game.getSideResp()) {
                side = game.getSideResp();
            } else {
                /*TODO -> Exception*/
                side = null;
            }
        }

        SideDto sideDto = null; // TODO -> replace with converter

        return sideDto;
    }

    /**
     *
     * @param player
     * @param gameId
     * @return The challenge of the adversary
     */
    @RequestMapping(value = "/getGame", method = RequestMethod.GET)
    public Game getGame(@RequestAttribute("player") Player player, @PathVariable("gameId") Long gameId) {
        // TODO : cette méthode renvoiera la version DTO de Game qui ne contient pas le rack de l'adversaire
        Game game = gameRepository.findOne(gameId);

        if (game.getCurrPlayer() instanceof Ai) {
            return makeAiPLay(dictionaryLoader, game, (User) player);
        } else {
            /* TODO -> send message to player that it's not his turn */

            // The other player hasn't played yet, so we send back the previous challenge of the other player
            return game;
        }
    }

    private Game makeAiPLay(DictionaryLoader dictionaryLoader, Game game, User player) {
        List<ITile> word = new ArrayList<>();

        Side side = game.getSideResp();
        WordAnalyzer wa = new WordAnalyzer(dictionaryLoader.getDico(game.getLang()), side.getChallenge(), side.getPlayerRack());

        // get the words by score
        TreeMap<Integer, List<ITile>> wordsByScore = wa.getWordsByScore();

        // Get the size of the treemap
        int sizeWordsByScore = wordsByScore.size();

        if (sizeWordsByScore == 0) {
            /* The AI can't create a word, it pass */
        } else if (sizeWordsByScore == 1) {
            // The AI play the only best possible word
            word = new ArrayList<>(wordsByScore.firstEntry().getValue());
        } else {
            Random random = new Random();
            int index = 0;
            int lowerBound = sizeWordsByScore / 3;
            int middleUpperBound = (lowerBound * 2) + 1;

            switch (player.getLevel()) {
                case 1:
                    index = random.nextInt(lowerBound);
                    break;
                case 2:
                    index = random.nextInt(middleUpperBound) + lowerBound;
                    break;
                case 3:
                    index = random.nextInt(sizeWordsByScore - 1) + middleUpperBound;
                    break;
                case 4:
                    index = sizeWordsByScore - 1;
                    break;
                default:
                    /* Message d'erreur */
                    break;
            }

            // Get the List of tile chosen by the Ai
            word = new ArrayList<>(wordsByScore.values()).get(index);
        }

        // TODO : déplacer les tiles (word) du rack au challenge

        // get a list of the tile taken from the bag of the game
        List<Tile> newTiles = game.getBag().getXTile(Constants.PLAYER_RACK_SIZE -
                game.getSideOfPlayer(player).getPlayerRack().getRack().size());

        // Update the side of the Ai
        updateSide(game.getSideOfPlayer(player), game.getSideOfPlayer(player).getChallenge(), newTiles);

        /* TODO -> send swap tiles to other player */

        return game;
    }

    private void updateSide(Side side, Challenge challenge, List<Tile> newTiles) {
        // Update the score of the side of the player
        side.updateScore(challenge.getScore());

        // Create the answer for the history
        side.addAnswer(challenge.getWord(), side.getScore());

        // add the new tiles to the player Rack
        side.addTilesToPlayerRack(newTiles);
    }
}
