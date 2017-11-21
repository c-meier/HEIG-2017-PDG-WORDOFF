package ch.heigvd.wordoff.common.Dto.Game.Slots;

import java.util.Objects;

public abstract class LxSlotDto extends SlotDto {
    private int bonus;

    // Necessary for Jackson deserialization
    protected LxSlotDto(int bonus) {
        this.bonus = bonus;
    }

    public LxSlotDto(Short pos, int bonus) {
        super(pos);
        this.bonus = bonus;
    }

    @Override
    public int getScore() {
        return super.getScore() * bonus;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || (o instanceof LxSlotDto && super.equals(o) && Objects.equals(bonus, ((LxSlotDto) o).bonus));
    }
}
