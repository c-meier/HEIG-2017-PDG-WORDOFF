package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.Side;

import javax.persistence.Entity;

@Entity
public class L2 extends Lx {

    private static int bonus = 2;

    public L2() {
        super(bonus);
    }

    public L2(Side side, Short pos) {
        super(side, pos, bonus);
    }
}
