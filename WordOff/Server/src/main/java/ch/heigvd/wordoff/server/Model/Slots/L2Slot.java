package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.server.Model.Side;

public class L2Slot extends LxSlot {


    private static int bonus = 2;

    public L2Slot() {
        super(bonus);
    }

    public L2Slot(Short pos) {
        super(pos, bonus);
    }
}
