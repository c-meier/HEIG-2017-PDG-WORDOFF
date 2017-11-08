package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.server.Model.Side;

public class LastSlot extends Slot {
    private final int bonus = 10;

    public LastSlot(){
    }

    public LastSlot(Side side, Short pos) {
        super(side, pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + bonus;
    }

}
