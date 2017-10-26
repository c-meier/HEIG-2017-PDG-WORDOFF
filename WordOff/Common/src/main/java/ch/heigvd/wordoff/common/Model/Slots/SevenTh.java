package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.Side;

import javax.persistence.Entity;

@Entity
public class SevenTh extends Slot {
    private final int bonus = 10;

    public SevenTh(){
    }

    public SevenTh(Side side, Short pos) {
        super(side, pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + bonus;
    }

}
