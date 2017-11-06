package ch.heigvd.wordoff.common.Dto.Tiles;

import ch.heigvd.wordoff.common.IModel.ITile;

public class TileDto implements ITile {
    private final Integer id;
    private char valueLetter;
    private int scoreLetter;

    public TileDto(int id, char valueLetter, int scoreLetter) {
        this.id = id;
        this.valueLetter = valueLetter;
        this.scoreLetter = scoreLetter;
    }

    public char getValue() {
        return valueLetter;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return scoreLetter;
    }
}
