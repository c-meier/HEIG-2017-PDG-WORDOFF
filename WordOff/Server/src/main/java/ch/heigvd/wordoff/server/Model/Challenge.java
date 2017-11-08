package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.IModel.IChallenge;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Challenge implements IChallenge, Serializable {
    private SwapRack swapRack;

    private List<ISlot> slots;

    protected Challenge() {
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

    public List<ITile> getTilesToSwap() {
        List<ITile> sTiles = new ArrayList<>();
        for (ISlot s : getSlots()) {
            if (s.getClass() == SwapSlotDto.class) {
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
