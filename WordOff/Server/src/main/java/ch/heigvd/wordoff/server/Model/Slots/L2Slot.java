package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.server.Model.Side;

import javax.persistence.Entity;

@Entity
public class L2Slot extends LxSlot {

    private static int bonus = 2;

    public L2Slot() {
        super(bonus);
    }

    public L2Slot(Side side, Short pos) {
        super(side, pos, bonus);
    }
}
