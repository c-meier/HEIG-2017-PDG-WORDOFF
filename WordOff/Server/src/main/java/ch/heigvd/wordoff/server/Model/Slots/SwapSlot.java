package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.server.Model.Side;

import javax.persistence.Entity;

@Entity
public class SwapSlot extends Slot {

    public SwapSlot() {

    }

    public SwapSlot(Side side, Short pos) {
        super(side, pos);
    }
}
