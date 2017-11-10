package ch.heigvd.wordoff.common.Dto.Racks;

import ch.heigvd.wordoff.common.Dto.IDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ITile;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Objects;

public abstract class RackDto implements IRack, IDto {
    @JsonDeserialize(contentAs = TileDto.class)
    private List<ITile> tiles;

    // Necessary for Jackson deserialization
    protected RackDto() {}

    public RackDto(List<ITile> tiles) {
        this.tiles = tiles;
    }

    public List<ITile> getTiles() {
        return tiles;
    }

    public void setTiles(List<ITile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof RackDto)) {
            return false;
        }
        RackDto r = (RackDto) o;
        return Objects.equals(tiles, r.tiles);
    }

    @Override
    public boolean isWellformed() {
        for(ITile t : getTiles()) {
            if(!(t instanceof TileDto) || !((TileDto) t).isWellformed()) {
                return false;
            }
        }
        return true;
    }
}
