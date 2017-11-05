package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.SideDto;

public class L2SlotDto extends LxSlotDto {

    private static int bonus = 2;

    public L2SlotDto(SideDto side, Short pos) {
        super(side, pos, bonus);
    }
}
