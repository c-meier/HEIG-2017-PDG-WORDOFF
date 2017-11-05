package ch.heigvd.wordoff.common.Dto;

import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class SideDto {

    private Long id;

    private PlayerSummaryDto player;

    private ChallengeDto challenge;

    private PlayerRackDto playerRack;

    private List<AnswerDto> answers;

    private int score;

    private short answerCounter;

    public SideDto(Long id, PlayerSummaryDto player, ChallengeDto challenge, PlayerRackDto playerRack,
                   List<AnswerDto> answers, int score, short answerCounter) {
        this.id = id;
        this.playerRack = playerRack;
        this.answers = answers;
        this.score = score;
        this.answerCounter = answerCounter;
        this.player = player;
        this.challenge = challenge;
    }

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

    public PlayerSummaryDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerSummaryDto player) {
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
