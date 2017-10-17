package ch.heigvd.wordoff.logic;

public class SevenTh extends Slot {
    private final int bonus = 10;

    public SevenTh(){
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + bonus;
    }

}
