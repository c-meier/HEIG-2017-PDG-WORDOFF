package ch.heigvd.wordoff.common.Dto.Slots;

public class L3SlotDto extends LxSlotDto {
    private static int bonus = 3;

    // Necessary for Jackson deserialization
    public L3SlotDto() {
        super(bonus);
    }

    public L3SlotDto(Short pos) {
        super(pos, bonus);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof L3SlotDto && super.equals(o);
    }
}
