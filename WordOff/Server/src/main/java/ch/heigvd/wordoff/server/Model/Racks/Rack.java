package ch.heigvd.wordoff.server.Model.Racks;

import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class Rack implements IRack {

    @OneToMany(targetEntity = Tile.class)
    private List<ITile> tiles;

    public Rack() {
        tiles = new ArrayList<>();
    }

    public List<ITile> getRack() {
        return tiles;
    }

    public void setTiles(List<ITile> tiles) {
        this.tiles = tiles;
    }
}
