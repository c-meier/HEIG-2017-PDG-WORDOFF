package ch.heigvd.wordoff.common.Dto;

import java.util.Objects;

public class AnswerDto {

    private Long sideId;

    private Short num;

    private ChallengeDto challenge;

    // Necessary for Jackson deserialization
    protected AnswerDto() {}

    public AnswerDto(Long sideId, Short num, ChallengeDto challenge) {
        this.sideId = sideId;
        this.num = num;
        this.challenge = challenge;
    }

    public ChallengeDto getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeDto challenge) {
        this.challenge = challenge;
    }

    public Long getSideId() {
        return sideId;
    }

    public void setSideId(Long sideId) {
        this.sideId = sideId;
    }

    public Short getNum() {
        return num;
    }

    public void setNum(Short num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AnswerDto)) {
            return false;
        }
        AnswerDto c = (AnswerDto) o;
        return Objects.equals(sideId, c.sideId) &&
                Objects.equals(num, c.num) &&
                Objects.equals(challenge, c.challenge);
    }
}
