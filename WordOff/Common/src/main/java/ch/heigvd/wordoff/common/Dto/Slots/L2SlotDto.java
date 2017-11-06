package ch.heigvd.wordoff.common.Dto.Slots;

public class L2SlotDto extends LxSlotDto {

    private static int bonus = 2;

    public L2SlotDto(Long sideId, Short pos) {
        super(sideId, pos, bonus);
    }
}
