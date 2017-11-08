package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Side;

import java.io.Serializable;

public class Slot implements ISlot, Serializable {
    Short pos;

    private ITile tile;

    public Slot() {
        this.pos = 1;
        this.tile = null;
    }
    public Slot(Side side, Short pos) {
        this.pos = pos;
        this.tile = null;
    }

    public ITile getTile(){
        return tile;
    }

    @Override
    public void setTile(ITile tile) {
        this.tile = tile;
    }

    public int getScore() {
        return null == tile ? 0 : tile.getScore();
    }

}
