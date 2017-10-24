package ch.heigvd.wordoff.common.Model.Slots;
import ch.heigvd.wordoff.common.Model.Answer;
import ch.heigvd.wordoff.common.Model.Side;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;
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
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Slot {

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

    @JsonIgnore
    @MapsId("sideId")
    @ManyToOne
    Side side;

    @ManyToOne
    private Tile tile;

    public Slot() {
        this.id = new SlotId();
        this.tile = null;
    }
    public Slot(Side side, Short pos) {
        this.side = side;
        this.id = new SlotId(side, pos);
        this.tile = null;
    }

    public boolean addTile(Tile tile) {
        if (isEmpty()) {
            this.tile = tile;
            return true;
        }
        return false;
    }

    @JsonIgnore
    public boolean isEmpty() {
        if (null == tile) {
            return true;
        }
        return false;
    }

    public Tile removeTile() {
        Tile temp = tile;
        tile = null;
        return temp;
    }

    public Tile getTile(){
        return tile;
    }

    @JsonIgnore
    public int getScore() {
        return null == tile ? 0 : tile.getScore();
    }

    public SlotId getId() {
        return id;
    }

}
