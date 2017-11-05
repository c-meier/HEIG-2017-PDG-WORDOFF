package ch.heigvd.wordoff.common.Model.Tiles;

import ch.heigvd.wordoff.common.IModel.ITile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

public class TileDto implements ITile {
    private Integer id;

    private LetterDto letter;

//    @JsonIgnore
//    private TileSetDto tileSet;

    public TileDto(int id, LetterDto letter) {
        this.id = id;
        this.letter = letter;
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

//    public void setTileSet(TileSetDto tileSet) {
//        this.tileSet = tileSet;
//    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LetterDto getLetter() {
        return letter;
    }

    public void setLetter(LetterDto letter) {
        this.letter = letter;
    }

//    public TileSetDto getTileSet() {
//        return tileSet;
//    }
}
