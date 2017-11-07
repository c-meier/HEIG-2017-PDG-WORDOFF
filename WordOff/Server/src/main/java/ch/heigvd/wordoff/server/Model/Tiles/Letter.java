package ch.heigvd.wordoff.server.Model.Tiles;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Letter {
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

    @Id
    @GeneratedValue
    private Integer id;

    private char value;
    private int score;

    @ManyToOne(targetEntity = LangSet.class)
    private LangSet langSet;

    protected Letter() {}

    public Letter(char value, int score) {
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
