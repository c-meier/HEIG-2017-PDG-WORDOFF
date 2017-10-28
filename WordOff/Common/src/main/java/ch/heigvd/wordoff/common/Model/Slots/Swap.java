package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.Side;

import javax.persistence.Entity;

@Entity
public class Swap extends Slot {

    public Swap() {

    }

    public Swap(Side side, Short pos) {
        super(side, pos);
    }
}
