/*
 * File: SlotDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.Game.Slots;

import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import ch.heigvd.wordoff.common.Dto.IDto;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "dtype"
)
public class SlotDto implements ISlot, IDto {
    private Short pos;

    @JsonDeserialize(as = TileDto.class)
    private ITile tile;

    // Necessary for Jackson deserialization
    public SlotDto() {}

    public SlotDto(Short pos) {
        this.pos = pos;
        this.tile = null;
    }

    public ITile getTile(){
        return tile;
    }

    public void setTile(ITile tile) {
        this.tile = tile;
    }

    @JsonIgnore
    public int getScore() {
        return null == tile ? 0 : tile.getScore();
    }

    @Override
    public ISlot duplicate() {
        SlotDto copy = new SlotDto(new Short(pos.shortValue()));
        copy.setTile(tile == null ? null : tile.duplicate());
        return copy;
    }

    public Short getPos() {
        return pos;
    }

    public void setPos(Short pos) {
        this.pos = pos;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof SlotDto)) {
            return false;
        }
        SlotDto s = (SlotDto) o;
        return Objects.equals(pos, s.pos) &&
                Objects.equals(tile, s.tile);
    }

    @Override
    public boolean isWellformed() {
        return pos != null &&
                ((tile == null) || (tile instanceof TileDto && ((TileDto)tile).isWellformed()));
    }
}
