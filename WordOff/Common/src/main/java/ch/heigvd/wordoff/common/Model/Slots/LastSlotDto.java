package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.SideDto;

import javax.persistence.Entity;

public class LastSlotDto extends SlotDto {
    private final int bonus = 10;

    public LastSlotDto(SideDto side, Short pos) {
        super(side, pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + bonus;
    }
}