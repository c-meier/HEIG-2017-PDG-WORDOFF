package ch.heigvd.wordoff.server.Model.Slots;

/**
 * Class that represent a slot that can double the score of a letter.
 */
public class L2Slot extends LxSlot {

    private static int bonus = 2;

    public L2Slot() {
        super(bonus);
    }

    public L2Slot(Short pos) {
        super(pos, bonus);
    }
}
