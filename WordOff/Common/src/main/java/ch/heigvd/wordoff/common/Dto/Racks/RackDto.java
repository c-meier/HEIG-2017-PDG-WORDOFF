package ch.heigvd.wordoff.common.Dto.Racks;

import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.util.List;

public abstract class RackDto implements IRack {

    private List<ITile> tiles;

    public RackDto(List<ITile> tiles) {
        this.tiles = tiles;
    }

    public List<ITile> getTiles() {
        return tiles;
    }

    public void setTiles(List<ITile> tiles) {
        this.tiles = tiles;
    }
}
