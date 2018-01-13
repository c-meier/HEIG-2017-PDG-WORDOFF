package ch.heigvd.wordoff.server.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class that represents a player's answer to a challenge.
 */
@Entity
public class Answer {
    @Lob
    private Challenge challenge;

    @EmbeddedId
    private AnswerId id;

    @MapsId("sideId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Side side;

    public Answer(Side side, Short num, Challenge challenge) {
        this.id = new AnswerId(side, num);
        this.side = side;
        this.challenge = challenge;
    }

    protected Answer() {
        this.id = new AnswerId();
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public AnswerId getId() {
        return id;
    }

    public void setId(AnswerId id) {
        this.id = id;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    @Embeddable
    public static class AnswerId implements Serializable {
        private Long sideId;

        private Short num;

        AnswerId() {}
        AnswerId(Side side, Short num) {
            this.sideId = side.getId();
            this.num = num;
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
            if (!(o instanceof AnswerId)) {
                return false;
            }
            AnswerId answerId = (AnswerId) o;
            return Objects.equals(sideId, answerId.sideId) &&
                    Objects.equals(num, answerId.num);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sideId, num);
        }
    }
}
