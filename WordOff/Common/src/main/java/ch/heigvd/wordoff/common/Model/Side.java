package ch.heigvd.wordoff.common.Model;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.common.Model.Racks.SwapRack;
import ch.heigvd.wordoff.common.Model.Slots.Slot;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import javax.persistence.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
@Entity
public class Side {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    @Embedded
    private Challenge challenge;

    @Embedded
    private SwapRack swapRack;

    @Embedded
    private PlayerRack playerRack;

    @OneToMany(mappedBy = "side", cascade = CascadeType.ALL)
    private List<Answer> answers;

    private int score;

    private short answerCounter;

    public Side() {
        this.swapRack = new SwapRack();
        this.playerRack = new PlayerRack();
        this.answers = new ArrayList<>();
        List<Slot> slots = new ArrayList<>();
        for (int i = 0; i < Constants.PLAYER_RACK_SIZE; i++) {
            slots.add(new Slot());
        }
        this.challenge = new Challenge(slots);
        score = 0;
        answerCounter = 1;
    }

    public Side(Player player) {
        this();
        this.player = player;
    }

//    private BooleanProperty playerTurn;
//    private BooleanProperty walsActive;
//    private int score;
//    // How many time the player has pass
//    private int nbPass;
//
//    public Side(Player player, ArrayList<SlotType> slots, Dictionary dico) {
//        this.DICTIONARY = dico;
//        swapRack = new SwapRack();
//        playerRack = new PlayerRack();
//        this.player = player;
//        score = 0;
//        updateChallenge(slots);
//        nbPass = 0;
//    }
//
//    *
//     * @return true if the word is valid, else false
//     * @brief check if the word exist
//
//    public boolean checkWord() {
//        return challenge.checkWord();
//    }
//
//    *
//     * @brief Calculate the score for this side
//
//    public int getScoreWord() {
//        return swapRack.applyBonus(challenge.getScoreWord());
//    }
//
//    *
//     * @param newTiles  The list of tiles to add to the player rack
//     * @param swapTiles The list of tiles to add to the swap rack if this side
//     * @brief Update the side rack with
//
//    public void fillRacks(List<Tile> newTiles, List<Tile> swapTiles) {
//        for (int i = 0; i < newTiles.size(); i++) {
//            playerRack.addTile(newTiles.get(i));
//        }
//
//        for (int i = 0; i < swapTiles.size(); i++) {
//            swapRack.addTile(swapTiles.get(i));
//        }
//    }
//
//    public void updateChallenge(ArrayList<SlotType> slots) {
//        this.slots = slots;
//        challenge = new Challenge(slots, DICTIONARY);
//    }
//
//    public int getScore() {
//        return score;
//    }
//
    public void updateScore(int challengeScore) {
        score += challengeScore;
    }

    public void addTilesToPlayerRack(List<Tile> newTiles) {
        for (int i = 0; i < newTiles.size(); i++) {
            playerRack.addTile(newTiles.get(i));
        }
    }

    public void addAnswer(String word, int score) {
        answers.add(new Answer(this, answerCounter, word, score));
    }

    public void addTileToSwapRack(Tile t) {
        swapRack.addTile(t);
    }

    public Long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public SwapRack getSwapRack() {
        return swapRack;
    }

    public void setSwapRack(SwapRack swapRack) {
        this.swapRack = swapRack;
    }

    public PlayerRack getPlayerRack() {
        return playerRack;
    }

    public void setPlayerRack(PlayerRack playerRack) {
        this.playerRack = playerRack;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public short getAnswerCounter() {
        return answerCounter;
    }

    public void setAnswerCounter(short answerCounter) {
        this.answerCounter = answerCounter;
    }
}
