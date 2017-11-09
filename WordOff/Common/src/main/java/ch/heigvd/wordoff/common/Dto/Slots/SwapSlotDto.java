package ch.heigvd.wordoff.common.Dto.Slots;

public class SwapSlotDto extends SlotDto {
    // Necessary for Jackson deserialization
    protected SwapSlotDto() {}

    public SwapSlotDto(Long sideId, Short pos) {
        super(sideId, pos);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof SwapSlotDto && super.equals(o);
    }
}
