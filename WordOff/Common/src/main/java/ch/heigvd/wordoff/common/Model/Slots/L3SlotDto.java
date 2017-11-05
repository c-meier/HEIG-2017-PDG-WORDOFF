package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.SideDto;

import javax.persistence.Entity;

public class L3SlotDto extends LxSlotDto {
    private static int bonus = 3;
    public L3SlotDto(SideDto side, Short pos) {
        super(side, pos, bonus);
    }
}
