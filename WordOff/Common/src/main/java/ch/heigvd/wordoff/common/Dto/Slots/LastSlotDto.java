package ch.heigvd.wordoff.common.Dto.Slots;

public class LastSlotDto extends SlotDto {
    private final int bonus = 10;

    public LastSlotDto(Long sideId, Short pos) {
        super(sideId, pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + bonus;
    }
}