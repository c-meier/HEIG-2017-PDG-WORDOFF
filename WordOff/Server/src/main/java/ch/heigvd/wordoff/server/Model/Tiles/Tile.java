package ch.heigvd.wordoff.server.Model.Tiles;

import ch.heigvd.wordoff.common.IModel.ITile;

import javax.persistence.*;

@Entity
public class Tile implements ITile {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(targetEntity = Letter.class, cascade = CascadeType.ALL)
    private Letter letter;

    @ManyToOne(targetEntity = LangSet.class)
    private LangSet langSet;

    protected Tile() {}

    public Tile(int id, char value, int score) {
        this.id = id;
        this.letter = new Letter(value, score);
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

    public void setLangSet(LangSet langSet) {
        this.langSet = langSet;
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

    public LangSet getLangSet() {
        return langSet;
    }
}
