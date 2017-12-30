package ch.heigvd.wordoff.server.Model.Slots;

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
