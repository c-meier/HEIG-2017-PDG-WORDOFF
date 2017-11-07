package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.IModel.IChallenge;
import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Dto.Slots.SwapSlotDto;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Slots.Slot;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Challenge implements IChallenge {

    @Embedded
    private SwapRack swapRack;

    @OneToMany(targetEntity = Slot.class, mappedBy = "side", cascade = CascadeType.ALL)
    private List<ISlot> slots;

    protected Challenge() {
        this.swapRack = new SwapRack();
        slots = new ArrayList<>();
    }
    public Challenge(List<ISlot> slots) {
        this.swapRack = new SwapRack();
        this.slots = slots;
    }
//
//    public ObservableList<Slot> getSlots() {
//        return slots;
//    }
//

    public SwapRack getSwapRack() {
        return swapRack;
    }

    public void setSwapRack(SwapRack swapRack) {
        this.swapRack = swapRack;
    }

//
//    private void endTurn() {
//        // Signal à side la fin du tour
//        // Soit Side vient prendre les infos, soit on lui envoit
//    }
//
//    public boolean playTurn() {
//        // Jouer le tour
//        if (checkWord() == true) {
//            endTurn();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public int getSizeChallenge() {
//        return Constants.CHALLENGE_SIZE;
//    }
//
//
//    public boolean addTileToPos(TileDto tile, int pos) {
//        if (slots.get(pos).isEmpty()) {
//            slots.get(pos).addTile(tile);
//            return true;
//        }
//        return false;
//    }
//
//    public TileDto getTileToPos(int pos) {
//        if (!slots.get(pos).isEmpty()) {
//            return slots.get(pos).removeTile();
//        }
//        return null;
//    }
//
//    public void moveTo(int fromPos, int toPos) {
//        if (!slots.get(fromPos).isEmpty() && slots.get(toPos).isEmpty()) {
//            slots.get(toPos).addTile(slots.get(fromPos).removeTile());
//        }
//    }
    
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
