package ch.heigvd.wordoff.common.Model;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Model.Racks.SwapRack;
import ch.heigvd.wordoff.common.Model.Slots.*;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Challenge {

    @Embedded
    private SwapRack swapRack;

    @OneToMany(mappedBy = "side", cascade = CascadeType.ALL)
    private List<Slot> slots;

    protected Challenge() {
        this.swapRack = new SwapRack();
        slots = new ArrayList<>();
    }
    public Challenge(List<Slot> slots) {
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

    public String getWord() {
        String word = "";
        for (Slot s : slots) {
            if (s.getTile() != null) {
                char c = s.getTile().getValue();
                word += c;
            }
        }
        return word;
    }

    public int getScoreWord() {
        int score = 0;
        for (Slot s : getSlots()) {
            score += s.getScore();
        }
        return swapRack.applyBonus(score);
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
    public boolean addTile(Tile tile) {
        for (Slot s : getSlots()) {
            if (s.isEmpty()) {
                return s.addTile(tile);
            }
        }
        return false;
    }
//
//    public boolean addTileToPos(Tile tile, int pos) {
//        if (slots.get(pos).isEmpty()) {
//            slots.get(pos).addTile(tile);
//            return true;
//        }
//        return false;
//    }
//
//    public Tile getTileToPos(int pos) {
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

    public List<Tile> getTilesToSwap() {
        List<Tile> sTiles = new ArrayList<>();
        for (Slot s : getSlots()) {
            if (s.getClass() == Swap.class) {
                sTiles.add(s.getTile());
            }
        }
        return sTiles;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public Tile getTileById(int id){
        for(Slot s : slots){
            if(!s.isEmpty()){
                if(s.getTile().getId() == id){
                    return s.removeTile();
                }
            }
        }
        return null;
    }
}
