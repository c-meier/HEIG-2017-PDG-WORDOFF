package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.Side;

public abstract class Lx extends Slot {
    private int bonus;

    public Lx(int bonus) {
        this.bonus = bonus;
    }

    public Lx(Side side, Short pos, int bonus) {
        super(side, pos);
        this.bonus = bonus;
    }

    @Override
    public int getScore() {
        return super.getScore() * bonus;
    }
}
