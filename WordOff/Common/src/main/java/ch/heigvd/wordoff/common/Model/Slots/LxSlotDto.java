package ch.heigvd.wordoff.common.Model.Slots;

import ch.heigvd.wordoff.common.Model.SideDto;

public abstract class LxSlotDto extends SlotDto {
    private int bonus;

    public LxSlotDto(SideDto side, Short pos, int bonus) {
        super(side, pos);
        this.bonus = bonus;
    }

    @Override
    public int getScore() {
        return super.getScore() * bonus;
    }
}
