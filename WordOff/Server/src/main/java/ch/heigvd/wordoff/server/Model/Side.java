package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.IModel.ITile;
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

    @ManyToOne
    private Player player;

    @Lob
    private Challenge challenge;

    @Lob
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

    public void updateScore(int challengeScore) {
        score += challengeScore;
    }

    public void addTilesToPlayerRack(List<Tile> newTiles) {
        for (Tile tile : newTiles) {
            playerRack.addTile(tile);
        }
    }

    public void addAnswer(Challenge challenge) {
        answers.add(new Answer(this, answerCounter++, challenge));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
