package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.IModel.IChallenge;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Slots.Slot;
import ch.heigvd.wordoff.server.Model.Slots.SwapSlot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Challenge implements IChallenge, Serializable {
    private SwapRack swapRack;

    @JsonDeserialize(contentAs = Slot.class)
    @JsonSerialize(contentAs = Slot.class)
    private List<ISlot> slots;

    public Challenge() {
        this.swapRack = new SwapRack();
        slots = new ArrayList<>();
    }

    public Challenge(List<ISlot> slots) {
        this.swapRack = new SwapRack();
        this.slots = slots;
    }

    public SwapRack getSwapRack() {
        return swapRack;
    }

    public void setSwapRack(SwapRack swapRack) {
        this.swapRack = swapRack;
    }

    @JsonIgnore
    public List<ITile> getTilesToSwap() {
        List<ITile> sTiles = new ArrayList<>();
        for (ISlot s : getSlots()) {
            if (s.getClass() == SwapSlot.class) {
                sTiles.add(s.getTile());
            }
        }
        return sTiles;
    }

    public List<ISlot> getSlots() {
        return slots;
    }

    public void setSlots(List<ISlot> slots) {
        this.slots = slots;
    }
}
