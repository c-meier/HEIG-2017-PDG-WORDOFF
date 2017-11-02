package ch.heigvd.wordoff.common.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class AnswerDto {
    public static class AnswerId implements Serializable {
        private Long sideId;

        private Short num;

        public AnswerId() {}
        public AnswerId(SideDto side, Short num) {
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
            if (!(o instanceof AnswerDto)) {
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

    private AnswerId id;

    @JsonIgnore
    private SideDto side;

    private String word;

    private int score;

    protected AnswerDto() {
        this.id = new AnswerId();
    }

    public AnswerDto(SideDto side, Short num, String word, int score) {
        this.id = new AnswerId(side, num);
        this.side = side;
        this.word = word;
        this.score = score;
    }

    public AnswerId getId() {
        return id;
    }

    public void setId(AnswerId id) {
        this.id = id;
    }

    public SideDto getSide() {
        return side;
    }

    public void setSide(SideDto side) {
        this.side = side;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
