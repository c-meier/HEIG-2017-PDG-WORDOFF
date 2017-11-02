package ch.heigvd.wordoff.server.Model.Slots;

import ch.heigvd.wordoff.server.Model.Side;

public abstract class LxSlot extends Slot {
    private int bonus;

    public LxSlot(int bonus) {
        this.bonus = bonus;
    }

    public LxSlot(Side side, Short pos, int bonus) {
        super(side, pos);
        this.bonus = bonus;
    }

    @Override
    public int getScore() {
        return super.getScore() * bonus;
    }
}
