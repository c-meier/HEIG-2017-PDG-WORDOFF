package ch.heigvd.wordoff.common.Dto;

import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;

import java.util.List;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class OtherSideDto {

    private Long id;

    private PlayerDto player;

    private ChallengeDto challenge;

    private int score;

    public OtherSideDto(Long id, PlayerDto player, ChallengeDto challenge,
                        int score) {
        this.id = id;
        this.score = score;
        this.player = player;
        this.challenge = challenge;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
