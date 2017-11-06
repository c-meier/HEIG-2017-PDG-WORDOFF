package ch.heigvd.wordoff.common.Dto.Slots;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Dto.AnswerDto;
import ch.heigvd.wordoff.common.Dto.SideDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "dtype"
)
public class SlotDto implements ISlot{

    Long sideId;

    Short pos;

    private ITile tile;

    public SlotDto(Long sideId, Short pos) {
        this.sideId = sideId;
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
