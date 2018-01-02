package ch.heigvd.wordoff.server.Model.Slots;

/**
 * Abstract class that represent a slot that multiply the score of a tile.
 */
public abstract class LxSlot extends Slot {
    private int bonus;

    public LxSlot(int bonus) {
        this.bonus = bonus;
    }

    public LxSlot(Short pos, int bonus) {
        super(pos);
        this.bonus = bonus;
    }

    @Override
    public int getScore() {
        return super.getScore() * bonus;
    }

    public int getBonus() {
        return bonus;
    }
}
