package ch.heigvd.wordoff.common.Dto.Slots;

import ch.heigvd.wordoff.common.Dto.SideDto;

public class L3SlotDto extends LxSlotDto {
    private static int bonus = 3;

//    public L3SlotDto(){
//        super(bonus);
//    }

    public L3SlotDto(SideDto side, Short pos) {
        super(side, pos, bonus);
    }
}
