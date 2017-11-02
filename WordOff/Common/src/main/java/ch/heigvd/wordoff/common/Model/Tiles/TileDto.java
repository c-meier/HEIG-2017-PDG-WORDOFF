package ch.heigvd.wordoff.common.Model.Tiles;

import ch.heigvd.wordoff.common.IModel.ITile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

public class TileDto implements ITile {
    private Integer id;

    private LetterDto letter;

    @JsonIgnore
    private TileSetDto tileSet;

    protected TileDto() {}

    public TileDto(int id, char value, int score) {
        this.id = id;
        this.letter = new LetterDto(value, score);
    }

    public TileDto(char value, int score) {
        this.letter = new LetterDto(value, score);
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

    public void setTileSet(TileSetDto tileSet) {
        this.tileSet = tileSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LetterDto getLetter() {
        return letter;
    }

    public void setLetter(LetterDto letter) {
        this.letter = letter;
    }

    public TileSetDto getTileSet() {
        return tileSet;
    }
}
