package ch.heigvd.wordoff.common.Dto.Game.Slots;

public class LastSlotDto extends SlotDto {
    private final int bonus = 10;

    // Necessary for Jackson deserialization
    public LastSlotDto() {}

    public LastSlotDto(Short pos) {
        super(pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + bonus;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof LastSlotDto && super.equals(o);
    }
}