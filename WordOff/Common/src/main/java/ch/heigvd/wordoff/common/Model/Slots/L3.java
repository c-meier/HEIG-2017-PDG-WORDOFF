package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.Side;

import javax.persistence.Entity;

@Entity
public class L3 extends Lx {
    private static int bonus = 3;

    public L3() {
        super(bonus);
    }

    public L3(Side side, Short pos) {
        super(side, pos, bonus);
    }
}
