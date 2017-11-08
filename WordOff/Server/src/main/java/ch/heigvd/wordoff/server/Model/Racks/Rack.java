package ch.heigvd.wordoff.server.Model.Racks;

import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ITile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Rack implements IRack, Serializable {
    private List<ITile> tiles;

    public Rack() {
        tiles = new ArrayList<>();
    }

    public Rack(Rack rack) {
        this.tiles = new ArrayList<>(rack.getTiles());
    }

    public List<ITile> getTiles() {
        return tiles;
    }

    public void setTiles(List<ITile> tiles) {
        this.tiles = tiles;
    }
}
