package ch.heigvd.wordoff.common.Dto.Slots;

public class L2SlotDto extends LxSlotDto {

    private static int bonus = 2;

    // Necessary for Jackson deserialization
    protected L2SlotDto() {
        super(bonus);
    }

    public L2SlotDto(Long sideId, Short pos) {
        super(sideId, pos, bonus);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof L2SlotDto && super.equals(o);
    }
}
