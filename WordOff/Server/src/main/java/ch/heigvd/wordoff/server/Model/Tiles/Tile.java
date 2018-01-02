package ch.heigvd.wordoff.server.Model.Tiles;

import ch.heigvd.wordoff.common.IModel.ITile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class that represents a Tile object.
 */
@Entity
public class Tile implements ITile, Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(targetEntity = Letter.class, cascade = CascadeType.ALL)
    private Letter letter;

    @Column(name = "lang_set_id")
    private Integer langSetId;

    public Tile() {
        this.letter = new Letter();
    }

    public Tile(int id, char value, int score) {
        this.id = id;
        this.letter = new Letter(value, score);
    }

    public Tile(char value, int score) {
        this.letter = new Letter(value, score);
    }

    @JsonIgnore
    public char getValue() {
        return letter.getValue();
    }

    public int getId() {
        return id;
    }

    @JsonIgnore
    public int getScore() {
        return letter.getScore();
    }

    @Override
    public void setScore(int score) {
        letter.setScore(score);
    }

    @Override
    public void setValue(char c) {
        this.letter.setValue(c);
    }

    @Override
    public ITile duplicate() {
        return new Tile(id, getValue(), getScore());
    }

    public void setId(int id) {
        this.id = id;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public Integer getLangSetId() {
        return langSetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;

        Tile tile = (Tile) o;

        return id.equals(tile.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
