package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Answer;
import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;

import javax.persistence.*;
import java.util.ArrayList;
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
    private PlayerRack playerRack;

    @OneToMany(mappedBy = "side", cascade = CascadeType.ALL)
    private List<Answer> answers;

    private int score;

    private short answerCounter;

    public Side() {
        this.playerRack = new PlayerRack();
        this.answers = new ArrayList<>();
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
//    public SideDto(Player player, ArrayList<SlotType> slots, Dictionary dico) {
//        this.DICTIONARY = dico;
//        swapRack = new SwapRackDto();
//        playerRack = new PlayerRackDto();
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
//    public void fillRacks(List<Tile> newTiles, List<TileDto> swapTiles) {
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
        for (Tile tile : newTiles) {
            playerRack.addTile(tile);
        }
    }

    public void addAnswer(String word, int score) {
        answers.add(new Answer(this, answerCounter, word, score));
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

    public void setChallenge(List<ITile> tiles) {
        for (ITile t : tiles) {
            challenge.addTile(t);
        }
    }
}
