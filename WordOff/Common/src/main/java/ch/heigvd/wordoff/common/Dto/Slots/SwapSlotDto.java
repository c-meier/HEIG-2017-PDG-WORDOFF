package ch.heigvd.wordoff.common.Dto.Slots;

public class SwapSlotDto extends SlotDto {
    // Necessary for Jackson deserialization
    public SwapSlotDto() {}

    public SwapSlotDto(Short pos) {
        super(pos);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof SwapSlotDto && super.equals(o);
    }
}
