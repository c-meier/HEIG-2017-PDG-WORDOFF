package ch.heigvd.wordoff.common.Model.Slots;
import ch.heigvd.wordoff.common.Model.Side;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Slot {
    @Embeddable
    class SlotId implements Serializable {
        Long sideId;
        Short pos;
    }

    @EmbeddedId
    SlotId id;

    @MapsId("sideId")
    @OneToOne
    Side side;

    @OneToOne
    private Tile tile;

    public Slot() {
        this.id = new SlotId();
        this.tile = null;
    }

    public boolean addTile(Tile tile) {
        if (isEmpty()) {
            this.tile = tile;
            return true;
        }
        return false;
    }

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

    public int getScore() {
        return null == tile ? 0 : tile.getScore();
    }

}
