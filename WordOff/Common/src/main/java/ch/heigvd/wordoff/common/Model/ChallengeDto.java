package ch.heigvd.wordoff.common.Model;

import ch.heigvd.wordoff.common.IModel.IChallenge;
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

    private SwapRackDto swapRack;

    private List<ISlot> slots;

    protected ChallengeDto() {
        this.swapRack = new SwapRackDto();
        slots = new ArrayList<>();
    }
    public ChallengeDto(List<ISlot> slots) {
        this.swapRack = new SwapRackDto();
        this.slots = slots;
    }
//
//    public ObservableList<SlotDto> getSlots() {
//        return slots;
//    }
//

    public SwapRackDto getSwapRack() {
        return swapRack;
    }

    public void setSwapRack(SwapRackDto swapRack) {
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

    public List<ISlot> getSlots() {
        return slots;
    }

    public void setSlots(List<ISlot> slots) {
        this.slots = slots;
    }


}
