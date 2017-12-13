package ch.heigvd.wordoff.common.Dto.Game.Tiles;

import ch.heigvd.wordoff.common.Dto.IDto;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.util.Objects;

public class TileDto implements ITile, IDto {
    private Integer id;
    private char value;
    private int score;

    // Necessary for Jackson deserialization & modelMapping
    public TileDto() {}

    public TileDto(int id, char valueLetter, int scoreLetter) {
        this.id = id;
        this.value = valueLetter;
        this.score = scoreLetter;
    }

    public char getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setValue(char c) {
        value = c;
    }

    @Override
    public ITile duplicate() {
        return new TileDto(id, value, score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TileDto)) {
            return false;
        }
        TileDto t = (TileDto) o;
        return Objects.equals(id, t.id) &&
                Objects.equals(value, t.value) &&
                Objects.equals(score, t.score);
    }

    @Override
    public boolean isWellformed() {
        return id != null;
    }
}
