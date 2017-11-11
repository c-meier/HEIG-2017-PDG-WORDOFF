package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.server.Model.Side;

public class L3Slot extends LxSlot {

    private static int bonus = 3;

    public L3Slot() {
        super(bonus);
    }

    public L3Slot(Short pos) {
        super(pos, bonus);
    }
}
