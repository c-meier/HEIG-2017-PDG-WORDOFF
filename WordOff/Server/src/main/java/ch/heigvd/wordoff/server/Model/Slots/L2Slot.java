package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.server.Model.Side;

public class L2Slot extends LxSlot {

    private static int bonus = 2;

    public L2Slot() {
        super(bonus);
    }

    public L2Slot(Side side, Short pos) {
        super(side, pos, bonus);
    }

    public L2Slot(L2Slot l2Slot) {
        super(l2Slot);
    }
}
