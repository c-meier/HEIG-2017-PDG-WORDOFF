package ch.heigvd.wordoff.common.Model.Tiles;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class LetterDto {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private Integer id;

    private char value;
    private int score;

    protected LetterDto() {}

    public LetterDto(char value, int score) {
        this.value = value;
        this.score = score;
    }

    public char getValue() {
        return value;
    }

    public int getScore() {
        return score;
    }
}
