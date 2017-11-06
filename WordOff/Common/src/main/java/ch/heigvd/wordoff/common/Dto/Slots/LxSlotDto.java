package ch.heigvd.wordoff.common.Dto.Slots;

public abstract class LxSlotDto extends SlotDto {
    private int bonus;

    public LxSlotDto(Long sideId, Short pos, int bonus) {
        super(sideId, pos);
        this.bonus = bonus;
    }

    @Override
    public int getScore() {
        return super.getScore() * bonus;
    }
}
