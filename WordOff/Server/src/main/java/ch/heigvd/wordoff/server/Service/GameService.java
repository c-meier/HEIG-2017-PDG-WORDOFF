package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.DictionaryLoader;
import ch.heigvd.wordoff.common.Dto.Game.SideDto;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.common.WordAnalyzer;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import ch.heigvd.wordoff.server.Repository.SideRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import ch.heigvd.wordoff.server.Util.ChallengeFactory;
import ch.heigvd.wordoff.common.DictionaryLoader;
import ch.heigvd.wordoff.server.Util.DtoFactory;
import javafx.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Service used as an intermediary between the database and the game controller
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

        // TODO -> Need to be removed
        Player ai = playerRepository.findOne(1L);
        Player one = playerRepository.findOne(2L);
        Player two = playerRepository.findOne(3L);

        // TODO -> Need to be removed
        // create and convert the new games.
        // initGame( , one, ai, "fr");
        // initGame( , two, one, "fr");
    }

    /**
     * Play the word, if accpeted, given by the player
     * @param game The game to update
     * @param player The player that play the word
     * @param challenge The challenge sended by the player
     * @return The updated game if the word is accepted or the game with an error if the word is not accepted
     */
    public Game play(Game game, Player player, Challenge challenge) {
        // Load the dico
        dictionaryLoader.loadDictionary(game.getLang());

        // Check if it's the right player who try to play
        if (Objects.equals(game.getCurrPlayer().getId(), player.getId())) {
            String wordChallenge = challenge.getWord();

            // If the word doesn't exists
            if (!dictionaryLoader.getDico(game.getLang()).contains(wordChallenge)) {
                throw new ErrorCodeException(Protocol.INVALID_WORD, "The word is not in the dictionary !");
            }

            // get side of player
            Side side = game.getSideOfPlayer(player);

            // check if the challenge is possible with tiles that the player have
            int i = 0;
            boolean tileIsNotInSwapRacks;
            boolean tileIsNotInPlayerRacks;
            List<ITile> tempSwapRack = new ArrayList<>(side.getChallenge().getSwapRack().getTiles());
            List<ITile> tempRackPlayer = new ArrayList<>(side.getPlayerRack().getTiles());
            while (challenge.getWord().length() != side.getChallenge().getWord().length()) {
                ITile currTile = challenge.getSlots().get(i).getTile();
                if (!tempSwapRack.contains(currTile)) {
                    tileIsNotInSwapRacks = true;
                } else {
                    side.getChallenge().addTile(side.getChallenge().getSwapRack().removeTile(currTile.getId()));
                    tileIsNotInSwapRacks = false;
                }

                if (!tempRackPlayer.contains(currTile)) {
                    tileIsNotInPlayerRacks = true;
                } else {
                    side.getChallenge().addTile(side.getPlayerRack().removeTile(currTile.getId()));
                    tileIsNotInPlayerRacks = false;
                }

                if (tileIsNotInPlayerRacks && tileIsNotInSwapRacks) {
                    throw new ErrorCodeException(Protocol.CHEATING, "The tile is not in one of the player racks, are you trying to cheat ?");
                }
                i++;
            }

            // get a list of the tile taken from the bag of the game
            List<ITile> newTiles = game.getBag().getXTile(Constants.PLAYER_RACK_SIZE -
                    side.getPlayerRack().getTiles().size());

            // reset jokers' values
            for(ISlot slot : side.getChallenge().getSlots()) {
                if(slot.getTile() != null && slot.getTile().isJoker()) {
                    slot.getTile().setValue('#');
                }
            }

            // Send swap tiles to other player
            game.getSideOfPlayer(game.getOtherPlayer(player)).getChallenge().setSwapRack(new SwapRack());
            for (ITile tile : side.getChallenge().getTilesToSwap()) {
                if (tile != null) {
                    game.getSideOfPlayer(game.getOtherPlayer(player)).getChallenge().getSwapRack().addTile(tile);
                }
            }

            // Update the player side
            updatePlayerSide(side, side.getChallenge(), newTiles);

            // switch player
            game.setCurrPlayer(game.getOtherPlayer(player));
        } else {
            throw new ErrorCodeException(Protocol.NOT_YOUR_TURN, "Not player turn to play !");
        }

        gameRepository.save(game);

        return game;
    }

    /**
     * Make the Ai play a word
     * @param game The game to update
     * @param player The Ai
     * @return The updated game
     */
    public Game makeAiPLay(Game game, User player) {
        List<ITile> word = new ArrayList<>();

        // get the words by score
        List<Pair<Integer, List<ITile>>> wordsByScore = WordAnalyzer.getWordsByScore(dictionaryLoader.getDico(game.getLang()),
                game.getSideResp().getChallenge(), game.getSideResp().getPlayerRack());

        // Get the size of the treemap
        int sizeWordsByScore = wordsByScore.size();

        if (sizeWordsByScore == 0) {
            discard2(game, player);
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
                    throw new ErrorCodeException(Protocol.NON_EXISTANT_PLAYER_LVL, "This ai level is not handled !");
            }

            // Get the List of tile chosen by the Ai
            word = new ArrayList<>(wordsByScore.get(index).getValue());
        }

        // Move word to challenge
        game.getSideResp().setChallenge(word);

        game.getSideResp().getChallenge().getSwapRack().getTiles().removeAll(word);
        game.getSideResp().getPlayerRack().getTiles().removeAll(word);

        // get a list of the tile taken from the bag of the game
        List<ITile> newTiles = game.getBag().getXTile(Constants.PLAYER_RACK_SIZE -
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
     * Initialize a new game
     * @param p1 Player 1
     * @param p2 Player 2 (can be null and will be initialize as an AI
     * @return A new initialize game
     */
    public Game initGame(Mode mode, Player p1, Player p2) {
        LangSet langSet = langSetRepository.findByName(mode.getLang());
        Game game = null;

        // Initialize game with or without Ai
        if (p2 == null) {
            game = new Game(mode, p1, playerRepository.findOne(1L), langSet);
        } else {
            game = new Game(mode, p1, p2, langSet);
        }

        Side sideInit = game.getSideInit();
        Side sideResp = game.getSideResp();

        // Set players Racks
        PlayerRack p1R = sideInit.getPlayerRack();
        PlayerRack p2R = sideResp.getPlayerRack();
        p1R.setTiles(game.getBag().getXTile(7));
        p2R.setTiles(game.getBag().getXTile(7));

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
        game.setSideInit(sideRepository.save(sideInit));
        game.setSideResp(sideRepository.save(sideResp));

        // save the game
        gameRepository.save(game);

        // add the game in the list of the mode
        mode.getGames().add(game);

        return game;
    }

    /**
     * Passe le tour du joueur "player" après avoir remplacé 2 tuiles
     *
     * @param game partie concernée
     * @param player joueur qui passe son tour
     */
    public SideDto discard2(Game game, Player player) {
        if(!game.getCurrPlayer().getId().equals(player.getId())) {
            throw new ErrorCodeException(Protocol.NOT_YOUR_TURN, "Ce n'est pas à votre tour de jouer");
        }

        Random rand = new Random();
        PlayerRack rack =  game.getSideOfPlayer(player).getPlayerRack();

        // retire 2 tiles du rack
        rack.getTiles().remove(rand.nextInt(rack.getTiles().size()));
        rack.getTiles().remove(rand.nextInt(rack.getTiles().size()));

        // choisit 2 nouvelles tuiles
        List<ITile> newTiles = game.getBag().getXTile(2);

        // update
        updatePlayerSide(game.getSideOfPlayer(player), game.getSideOfPlayer(player).getChallenge(), newTiles);

        // switch player
        game.setCurrPlayer(game.getOtherPlayer(player));

        // Si l'adversaire a aussi passé son tour (sa dernière réponse est vide) et qu'il n'y a plus de pièces à ajouter
        // => fin du jeu
        List<Answer> opponentAnswers = game.getSideOfPlayer(game.getOtherPlayer(player)).getAnswers();
        if(newTiles.isEmpty() && opponentAnswers.get(opponentAnswers.size() - 1).getChallenge().getSlots().get(0).isEmpty()) {

            // TODO ajuster les classements des joueurs, donner une récompense en pièces ?

            game.setEnded(true);
        }

        gameRepository.save(game);

        return DtoFactory.createFrom(game.getSideOfPlayer(player));
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
    private void updatePlayerSide(Side side, Challenge challenge, List<ITile> newTiles) {
        // Update the score of the side of the player
        side.updateScore(challenge.getScore());

        // Create the answer for the history
        side.addAnswer(challenge);

        // add the new tiles to the player Rack
        side.addTilesToPlayerRack(newTiles);

        // Create new challenge
        side.setChallenge(new ChallengeFactory(side).createRandomSlotPos().create());
    }

    /**
     * Retourne le side de l'adversaire
     *
     * @param game
     * @param player le joueur qui demande le side de l'adversaire
     * @return SideDto
     */
    public SideDto peek(Game game, User player) {
        return DtoFactory.createFrom(game.getSideOfPlayer(game.getOtherPlayer(player)));
    }

    /**
     * jette toutes les tuiles et en pioches des nouvelles
     * @param game partie concernée
     * @param player joueur qui jette les tuiles
     * @return le side du joueur "player"
     */
    public SideDto discardAll(Game game, Player player) {
        if(!game.getCurrPlayer().getId().equals(player.getId())) {
            throw new ErrorCodeException(Protocol.NOT_YOUR_TURN, "Ce n'est pas à votre tour de jouer");
        }

        PlayerRack rack =  game.getSideOfPlayer(player).getPlayerRack();

        // retire toutes les tuiles du rack
        rack.getTiles().clear();

        // remplit le rack avec de nouvelles tuiles
        List<ITile> newTiles = game.getBag().getXTile(Constants.PLAYER_RACK_SIZE);
        game.getSideOfPlayer(player).addTilesToPlayerRack(newTiles);

        gameRepository.save(game);

        return DtoFactory.createFrom(game.getSideOfPlayer(player));
    }
}
