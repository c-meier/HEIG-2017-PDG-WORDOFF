package ch.heigvd.wordoff.common.Dto;

import ch.heigvd.wordoff.common.IModel.IChallenge;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.Dto.Racks.SwapRackDto;

import java.util.List;

public class ChallengeDto implements IChallenge {

    private IRack swapRack;

    private List<ISlot> slots;

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


}
