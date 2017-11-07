package ch.heigvd.wordoff.server.Model.Slots;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Answer;
import ch.heigvd.wordoff.server.Model.Side;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Slot implements ISlot {

    @Embeddable
    public static class SlotId implements Serializable {
        Long sideId;
        Short pos;

        public SlotId() {}
        public SlotId(Side side, Short pos) {
            this.sideId = side.getId();
            this.pos = pos;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Answer)) {
                return false;
            }
            Slot.SlotId slotId = (Slot.SlotId) o;
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

    @EmbeddedId
    SlotId id;

    @MapsId("sideId")
    @ManyToOne
    Side side;

    @ManyToOne(targetEntity = Tile.class)
    private ITile tile;

    public Slot() {
        this.id = new SlotId();
        this.tile = null;
    }
    public Slot(Side side, Short pos) {
        this.side = side;
        this.id = new SlotId(side, pos);
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

    public SlotId getId() {
        return id;
    }

}
