package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.WordAnalyzer;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import ch.heigvd.wordoff.server.Repository.SideRepository;
import ch.heigvd.wordoff.server.Rest.Exception.InvalidAiLevel;
import ch.heigvd.wordoff.server.Rest.Exception.InvalidWordException;
import ch.heigvd.wordoff.server.Rest.Exception.TileIsNotInRack;
import ch.heigvd.wordoff.server.Rest.Exception.WrongPlayer;
import ch.heigvd.wordoff.server.Util.ChallengeFactory;
import ch.heigvd.wordoff.server.Util.DictionaryLoader;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service used to s
 */
@Service
public class GameService {
    private DictionaryLoader dictionaryLoader;
    private LangSetRepository langSetRepository;
    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private SideRepository sideRepository;

    public GameService(DictionaryLoader dictionaryLoader, LangSetRepository langSetRepository, PlayerRepository playerRepository, GameRepository gameRepository, SideRepository sideRepository) {
        this.dictionaryLoader = dictionaryLoader;
        this.langSetRepository = langSetRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.sideRepository = sideRepository;
    }

    public Game play(Game game, Player player, Challenge challenge) {
        // Load the dico
        dictionaryLoader.loadDictionary(game.getLang());

        Side side = null;

        // Check if it's the right player who try to play
        if (player.equals(game.getCurrPlayer())) {
            String wordChallenge = challenge.getWord();

            // If the word doesn't exists
            if (!dictionaryLoader.getDico(game.getLang()).contains(wordChallenge)) {
                throw new InvalidWordException("The word is not in the dictionary !");
            }

            // check if the challenge is possible with tiles that the player have
            int i = 0;
            boolean tileIsNotInSwapRacks = false;
            boolean tileIsNotInPlayerRacks = false;
            List<ITile> tempRackPlayer = new ArrayList<>();
            List<ITile> tempSwapRack = new ArrayList<>();
            tempSwapRack.addAll(game.getSideOfPlayer(player).getChallenge().getSwapRack().getTiles());
            tempRackPlayer.addAll(game.getSideOfPlayer(player).getPlayerRack().getTiles());
            while (challenge.getWord().length() != game.getSideOfPlayer(player).getChallenge().getWord().length()) {
                ITile currTile = challenge.getSlots().get(i).getTile();
                if (i < tempSwapRack.size()) {
                    if (!tempSwapRack.contains(currTile)) {
                        tileIsNotInSwapRacks = true;
                    } else {
                        game.getSideOfPlayer(player).getChallenge().addTile(game.getSideOfPlayer(player).getChallenge().getSwapRack().removeTile(currTile.getId()));
                        game.getSideOfPlayer(player).getChallenge().getSwapRack().getTiles().remove(game.getSideOfPlayer(player).getChallenge().getSwapRack().removeTile(currTile.getId()));
                        tileIsNotInSwapRacks = false;
                    }
                }

                if (i < tempRackPlayer.size()) {
                    if (!tempRackPlayer.contains(currTile)) {
                        tileIsNotInPlayerRacks = true;
                    } else {
                        game.getSideOfPlayer(player).getChallenge().addTile(game.getSideOfPlayer(player).getPlayerRack().removeTile(currTile.getId()));
                        game.getSideOfPlayer(player).getPlayerRack().getTiles().remove(game.getSideOfPlayer(player).getPlayerRack().removeTile(currTile.getId()));
                        tileIsNotInPlayerRacks = false;
                    }
                }

                if (tileIsNotInPlayerRacks && tileIsNotInSwapRacks) {
                    throw new TileIsNotInRack("The tile is not in one of the player racks, are you trying to cheat ?");
                }
                i++;
            }

            // get a list of the tile taken from the bag of the game
            List<Tile> newTiles = game.getBag().getXTile(Constants.PLAYER_RACK_SIZE -
                    game.getSideOfPlayer(player).getPlayerRack().getTiles().size());

            // reset jokers' values
            for(ISlot slot : game.getSideOfPlayer(player).getChallenge().getSlots()) {
                if(slot.getTile() != null && slot.getTile().isJoker()) {
                    slot.getTile().setValue('#');
                }
            }

            // Send swap tiles to other player
            game.getSideOfPlayer(game.getOtherPlayer(player)).getChallenge().setSwapRack(new SwapRack());
            for (ITile tile : game.getSideOfPlayer(player).getChallenge().getTilesToSwap()) {
                if (tile != null) {
                    game.getSideOfPlayer(game.getOtherPlayer(player)).getChallenge().getSwapRack().addTile(tile);
                }
            }

            // Update the player side
            updatePlayerSide(game.getSideOfPlayer(player), game.getSideOfPlayer(player).getChallenge(), newTiles);

            // switch player
            game.setCurrPlayer(game.getOtherPlayer(player));

            // PlayerDto Side updated
            side = game.getSideOfPlayer(player);

        } else {
            throw new WrongPlayer("Not player turn to play !");
        }

        gameRepository.save(game);

        return game;
    }

    public Game makeAiPLay(Game game, User player) {
        List<ITile> word = new ArrayList<>();

        WordAnalyzer wa = new WordAnalyzer(dictionaryLoader.getDico(game.getLang()), game.getSideResp().getChallenge(), game.getSideResp().getPlayerRack());

        // get the words by score
        List<Pair<Integer, List<ITile>>> wordsByScore = wa.getWordsByScore();

        // Get the size of the treemap
        int sizeWordsByScore = wordsByScore.size();

        if (sizeWordsByScore == 0) {
            /* TODO -> The AI can't create a word, it pass */
        } else if (sizeWordsByScore == 1) {
            // The AI play the only best possible word
            word = new ArrayList<>(wordsByScore.get(0).getValue());
        } else {
            Random random = new Random();
            int index;
            int lowerBound = sizeWordsByScore / 3;
            int middleUpperBound = (lowerBound * 2) + 1;

            switch (player.getLevel()) {
                case 1:
                    index = random.nextInt(lowerBound+1);
                    break;
                case 2:
                    index = random.nextInt(middleUpperBound+1) + lowerBound;
                    break;
                case 3:
                    index = random.nextInt(sizeWordsByScore) + middleUpperBound;
                    break;
                case 4:
                    index = sizeWordsByScore - 1;
                    break;
                default:
                    throw new InvalidAiLevel("This ai level is not handled !");
            }

            // Get the List of tile chosen by the Ai
            word = new ArrayList<>(wordsByScore.get(index).getValue());
        }

        // Move word to challenge
        game.getSideResp().setChallenge(word);

        game.getSideResp().getChallenge().getSwapRack().getTiles().removeAll(word);
        game.getSideResp().getPlayerRack().getTiles().removeAll(word);

        // get a list of the tile taken from the bag of the game
        List<Tile> newTiles = game.getBag().getXTile(Constants.PLAYER_RACK_SIZE -
                game.getSideOfPlayer(player).getPlayerRack().getTiles().size());

        // reset jokers' values
        for(ISlot slot : game.getSideOfPlayer(player).getChallenge().getSlots()) {
            if(slot.getTile() != null && slot.getTile().isJoker()) {
                slot.getTile().setValue('#');
            }
        }

        // Send swap tiles to other player
        game.getSideOfPlayer(game.getOtherPlayer(player)).getChallenge().setSwapRack(new SwapRack());
        for (ITile tile : game.getSideResp().getChallenge().getTilesToSwap()) {
            if (tile != null) {
                game.getSideOfPlayer(game.getOtherPlayer(player)).getChallenge().getSwapRack().addTile(tile);
            }
        }

        // Update the side of the Ai
        updatePlayerSide(game.getSideResp(), game.getSideResp().getChallenge(), newTiles);

        // switch player
        game.setCurrPlayer(game.getOtherPlayer(player));

        gameRepository.save(game);

        return game;
    }

    /**
     * @brief Initialize a new game
     * @param p1 Player 1
     * @param p2 Player 2 (can be null and will be initialize as an AI)
     * @param lang language of the game
     * @return A new initialize game
     */
    public Game initGame(Player p1, Player p2, String lang) {
        LangSet langSet = langSetRepository.findByName(lang);
        Game game = null;

        // Initialize game with or without Ai
        if (p2 == null) {
            game = new Game(p1, playerRepository.findOne(1L), langSet);
        } else {
            game = new Game(p1, p2, langSet);
        }

        Side sideInit = game.getSideInit();
        Side sideResp = game.getSideResp();

        // Set players Racks
        PlayerRack p1R = sideInit.getPlayerRack();
        PlayerRack p2R = sideResp.getPlayerRack();
        p1R.setTiles(game.getBag().getSevenTiles());
        p2R.setTiles(game.getBag().getSevenTiles());

        // set new Challenge
        sideInit.setChallenge(new ChallengeFactory(sideInit).createRandomSlotPos().create());
        sideResp.setChallenge(new ChallengeFactory(sideResp).createRandomSlotPos().create());

        // Set swap racks
        SwapRack s1 = sideInit.getChallenge().getSwapRack();
        SwapRack s2 = sideResp.getChallenge().getSwapRack();
        s1.addTile(game.getBag().pop());
        s1.addTile(game.getBag().pop());
        s2.addTile(game.getBag().pop());
        s2.addTile(game.getBag().pop());
        sideInit.getChallenge().setSwapRack(s1);
        sideResp.getChallenge().setSwapRack(s2);

        // save the sides
        sideRepository.save(sideInit);
        sideRepository.save(sideResp);

        // save the game
        gameRepository.save(game);

        return game;
    }

    /**
     * @brief Update the player side
     * @param side Side of the player
     * @param challenge Challenge in the player side
     * @param newTiles New tiles taken from the bag
     * @details - update the score
     *          - create a log with the word and the score
     *          - add new tiles to player rack
     *          - create a new challenge
     */
    private void updatePlayerSide(Side side, Challenge challenge, List<Tile> newTiles) {
        // Update the score of the side of the player
        int score = challenge.getScore();
        side.updateScore(challenge.getScore());

        // Create the answer for the history
        side.addAnswer(challenge);

        // add the new tiles to the player Rack
        side.addTilesToPlayerRack(newTiles);

        // Create new challenge
        side.setChallenge(new ChallengeFactory(side).createRandomSlotPos().create());
    }
}
