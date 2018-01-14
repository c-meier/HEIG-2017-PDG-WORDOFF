package ch.heigvd.wordoff.server.Model.Slots;

import static ch.heigvd.wordoff.common.Constants.LAST_SLOT_BONUS;

/**
 * Class that represent the last slot in the challenge.
 * Give 10 points as a bonus at the final score
 */
public class LastSlot extends Slot {
    public LastSlot(){
    }

    public LastSlot(Short pos) {
        super(pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + LAST_SLOT_BONUS;
    }
}
