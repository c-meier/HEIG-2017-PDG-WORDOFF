package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Side;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "dtype"
)
public class Slot implements ISlot, Serializable {

    Short pos;

    @JsonDeserialize(as = Tile.class)
    private ITile tile;

    public Slot() {
        this.pos = 1;
        this.tile = null;
    }

    public Slot(Short pos) {
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

    public Short getPos() {
        return pos;
    }

    @Override
    public void setPos(Short pos) {
        this.pos = pos;
    }
}
