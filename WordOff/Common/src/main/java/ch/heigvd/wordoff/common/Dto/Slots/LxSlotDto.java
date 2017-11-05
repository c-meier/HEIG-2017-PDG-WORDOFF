package ch.heigvd.wordoff.common.Dto.Slots;

import ch.heigvd.wordoff.common.Dto.SideDto;

public abstract class LxSlotDto extends SlotDto {
    private int bonus;

//    public LxSlotDto(int bonus) {
//        this.bonus = bonus;
//    }

    public LxSlotDto(SideDto side, Short pos, int bonus) {
        super(side, pos);
        this.bonus = bonus;
    }

    @Override
    public int getScore() {
        return super.getScore() * bonus;
    }
}
