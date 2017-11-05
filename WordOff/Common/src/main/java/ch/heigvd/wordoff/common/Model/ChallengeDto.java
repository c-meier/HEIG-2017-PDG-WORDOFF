package ch.heigvd.wordoff.common.Model;

import ch.heigvd.wordoff.common.IModel.IChallenge;
import ch.heigvd.wordoff.common.IModel.IRack;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Model.Racks.SwapRackDto;
import ch.heigvd.wordoff.common.Model.Slots.*;
import ch.heigvd.wordoff.common.Model.Tiles.TileDto;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import java.util.ArrayList;
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
