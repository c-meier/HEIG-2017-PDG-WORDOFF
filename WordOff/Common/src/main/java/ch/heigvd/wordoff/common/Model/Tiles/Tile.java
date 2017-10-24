package ch.heigvd.wordoff.common.Model.Tiles;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Tile {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(targetEntity = Letter.class, cascade = CascadeType.ALL)
    private Letter letter;

    @JsonIgnore
    @ManyToOne(targetEntity = TileSet.class)
    private TileSet tileSet;

    protected Tile() {}

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

    public void setTileSet(TileSet tileSet) {
        this.tileSet = tileSet;
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

    public TileSet getTileSet() {
        return tileSet;
    }
}
