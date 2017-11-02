package ch.heigvd.wordoff.common.Model.Slots;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Model.AnswerDto;
import ch.heigvd.wordoff.common.Model.SideDto;
import ch.heigvd.wordoff.common.Model.Tiles.TileDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "dtype"
)
public class SlotDto implements ISlot{

    public static class SlotId implements Serializable {
        Long sideId;
        Short pos;

        public SlotId() {}
        public SlotId(SideDto side, Short pos) {
            this.sideId = side.getId();
            this.pos = pos;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof AnswerDto)) {
                return false;
            }
            SlotDto.SlotId slotId = (SlotDto.SlotId) o;
            return Objects.equals(sideId, slotId.sideId) &&
                    Objects.equals(pos, slotId.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sideId, pos);
        }

        public Long getSideId() {
            return sideId;
        }

        public void setSideId(Long sideId) {
            this.sideId = sideId;
        }

        public Short getPos() {
            return pos;
        }

        public void setPos(Short pos) {
            this.pos = pos;
        }
    }

    SlotId id;

    @JsonIgnore
    SideDto side;

    private ITile tile;

    public SlotDto() {
        this.id = new SlotId();
        this.tile = null;
    }
    public SlotDto(SideDto side, Short pos) {
        this.side = side;
        this.id = new SlotId(side, pos);
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

    public SlotId getId() {
        return id;
    }

}
