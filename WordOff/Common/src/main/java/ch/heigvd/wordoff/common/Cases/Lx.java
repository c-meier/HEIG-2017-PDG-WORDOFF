package ch.heigvd.wordoff.common.Cases;

public abstract class Lx extends Slot {
    private int bonus;

    public Lx(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public int getScore() {
        return super.getScore() * bonus;
    }
}
