package ch.heigvd.wordoff.common.Model;

import ch.heigvd.wordoff.common.Model.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Model.Tiles.TileDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class SideDto {

    private Long id;

    private PlayerDto player;

    private ChallengeDto challenge;

    private PlayerRackDto playerRack;

    private List<AnswerDto> answers;

    private int score;

    private short answerCounter;

    public SideDto() {
        this.playerRack = new PlayerRackDto();
        this.answers = new ArrayList<>();
        score = 0;
        answerCounter = 1;
    }

    public SideDto(PlayerDto player) {
        this();
        this.player = player;
    }

//    private BooleanProperty playerTurn;
//    private BooleanProperty walsActive;
//    private int score;
//    // How many time the player has pass
//    private int nbPass;
//
//    public SideDto(PlayerDto player, ArrayList<SlotType> slots, Dictionary dico) {
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
//    public void fillRacks(List<TileDto> newTiles, List<TileDto> swapTiles) {
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
//        challenge = new ChallengeDto(slots, DICTIONARY);
//    }
//
//    public int getScore() {
//        return score;
//    }
//
    public void updateScore(int challengeScore) {
        score += challengeScore;
    }

    public void addTilesToPlayerRack(List<TileDto> newTiles) {
        for (TileDto tile : newTiles) {
            playerRack.addTile(tile);
        }
    }

    public void addAnswer(String word, int score) {
        answers.add(new AnswerDto(this, answerCounter, word, score));
    }

    public Long getId() {
        return id;
    }

    public PlayerDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDto player) {
        this.player = player;
    }

    public ChallengeDto getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeDto challenge) {
        this.challenge = challenge;
    }

    public PlayerRackDto getPlayerRack() {
        return playerRack;
    }

    public void setPlayerRack(PlayerRackDto playerRack) {
        this.playerRack = playerRack;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
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
