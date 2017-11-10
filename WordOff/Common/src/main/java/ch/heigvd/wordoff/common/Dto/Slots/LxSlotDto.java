package ch.heigvd.wordoff.common.Dto.Slots;

public abstract class LxSlotDto extends SlotDto {
    private int bonus;

    // Necessary for Jackson deserialization
    protected LxSlotDto(int bonus) {
        this.bonus = bonus;
    }

    public LxSlotDto(Long sideId, Short pos, int bonus) {
        super(sideId, pos);
        this.bonus = bonus;
    }

    @Override
    public int getScore() {
        return super.getScore() * bonus;
    }
}
