/*
 * File: Slot.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

/**
 * Class that represent a simple (without bonus or malus) slot.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "dtype"
)
public class Slot implements ISlot, Serializable {
    /**
     * The position of the slot in the challenge.
     */
    Short pos;

    /**
     * The tile if it exists held in this slot.
     */
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

    @Override
    public ISlot duplicate() {
        Slot copy = new Slot(pos);
        copy.setTile(tile == null ? null : tile.duplicate());
        return copy;
    }
}
