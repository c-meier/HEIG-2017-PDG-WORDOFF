package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.SideDto;

import javax.persistence.Entity;

public class SwapSlotDto extends SlotDto {

    public SwapSlotDto() {

    }

    public SwapSlotDto(SideDto side, Short pos) {
        super(side, pos);
    }
}
