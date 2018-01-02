package ch.heigvd.wordoff.server.Model.Slots;

/**
 * Class that represent the last slot in the challenge.
 * Give 10 points as a bonus at the final score
 */
public class LastSlot extends Slot {

    private final int bonus = 10;

    public LastSlot(){
    }

    public LastSlot(Short pos) {
        super(pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + bonus;
    }

}
