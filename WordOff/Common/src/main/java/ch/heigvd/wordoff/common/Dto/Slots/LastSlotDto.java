package ch.heigvd.wordoff.common.Dto.Slots;

import ch.heigvd.wordoff.common.Dto.SideDto;

public class LastSlotDto extends SlotDto {
    private final int bonus = 10;

//    public LastSlotDto() {}

    public LastSlotDto(SideDto side, Short pos) {
        super(side, pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + bonus;
    }
}