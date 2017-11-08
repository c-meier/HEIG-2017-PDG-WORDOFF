package ch.heigvd.wordoff.server.Model.Tiles;

import ch.heigvd.wordoff.common.IModel.ITile;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Tile implements ITile, Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(targetEntity = Letter.class, cascade = CascadeType.ALL)
    private Letter letter;

    @Column(name = "lang_set_id")
    private Integer langSetId;

    protected Tile() {}

    public Tile(int id, char value, int score) {
        this.id = id;
        this.letter = new Letter(value, score);
    }

    public Tile(Tile tile) {
        this.id = tile.getId();
        this.letter = new Letter(tile.letter);
        this.langSetId = tile.getLangSetId();
    }

    public Tile(char value, int score) {
        this.letter = new Letter(value, score);
    }

    public char getValue() {
        return letter.getValue();
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return letter.getScore();
    }

    @Override
    public void setValue(char c) {
        this.letter.setValue(c);
    }

    public void setId(Integer id) {
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
}
