package ch.heigvd.wordoff.common.Dto;

import java.util.Objects;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class OtherSideDto {

    private Long id;

    private PlayerDto player;

    private ChallengeDto challenge;

    private int score;

    // Necessary for Jackson deserialization
    protected OtherSideDto() {}

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

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof OtherSideDto)) {
            return false;
        }
        OtherSideDto c = (OtherSideDto) o;
        return Objects.equals(id, c.id) &&
                Objects.equals(player, c.player) &&
                Objects.equals(challenge, c.challenge) &&
                Objects.equals(score, c.score);
    }
}
