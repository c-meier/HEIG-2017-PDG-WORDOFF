package ch.heigvd.wordoff.common.Dto.Game;

import ch.heigvd.wordoff.common.Dto.Game.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Dto.Game.Slots.SlotDto;
import ch.heigvd.wordoff.common.Dto.IDto;
import ch.heigvd.wordoff.common.IModel.IChallenge;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ISlot;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChallengeDto implements IChallenge, IDto {

    private IRack swapRack;

    @JsonDeserialize(contentAs = SlotDto.class)
    @JsonSerialize(contentAs = SlotDto.class)
    private List<ISlot> slots;

    // Necessary for Jackson deserialization
    protected ChallengeDto() {
        slots = new ArrayList<>();
    }

    public ChallengeDto(List<ISlot> slots, IRack swapRack) {
        this.swapRack = swapRack;
        this.slots = slots;
    }

    public IRack getSwapRack() {
        return swapRack;
    }

    public void setSwapRack(SwapRackDto swapRack) {
        this.swapRack = swapRack;
    }

    public List<ISlot> getSlots() {
        return slots;
    }

    public void setSlots(List<ISlot> slots) {
        this.slots = slots;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ChallengeDto)) {
            return false;
        }
        ChallengeDto c = (ChallengeDto) o;
        return Objects.equals(swapRack, c.swapRack) &&
                Objects.equals(slots, c.slots);
    }

    @Override
    public boolean isWellformed() {
        for(ISlot s : getSlots()) {
            if(!(s instanceof SlotDto) || !((SlotDto) s).isWellformed()) {
                return false;
            }
        }
        return (swapRack instanceof SwapRackDto) && ((SwapRackDto)swapRack).isWellformed();
    }
}
