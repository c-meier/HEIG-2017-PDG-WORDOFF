package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.server.Model.Side;

import javax.persistence.Entity;

@Entity
public class L3Slot extends LxSlot {
    private static int bonus = 3;

    public L3Slot() {
        super(bonus);
    }

    public L3Slot(Side side, Short pos) {
        super(side, pos, bonus);
    }
}
