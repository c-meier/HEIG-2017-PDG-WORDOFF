package ch.heigvd.wordoff.common.Dto.Slots;

public class L3SlotDto extends LxSlotDto {
    private static int bonus = 3;

    public L3SlotDto(Long sideId, Short pos) {
        super(sideId, pos, bonus);
    }
}
