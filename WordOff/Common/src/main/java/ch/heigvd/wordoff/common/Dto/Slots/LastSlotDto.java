package ch.heigvd.wordoff.common.Dto.Slots;

public class LastSlotDto extends SlotDto {
    private final int bonus = 10;

    // Necessary for Jackson deserialization
    protected LastSlotDto() {}

    public LastSlotDto(Long sideId, Short pos) {
        super(sideId, pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + bonus;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof LastSlotDto && super.equals(o);
    }
}