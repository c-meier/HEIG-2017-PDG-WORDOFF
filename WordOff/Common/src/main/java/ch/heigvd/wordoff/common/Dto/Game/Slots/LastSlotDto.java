package ch.heigvd.wordoff.common.Dto.Game.Slots;

import static ch.heigvd.wordoff.common.Constants.LAST_SLOT_BONUS;

public class LastSlotDto extends SlotDto {
    // Necessary for Jackson deserialization
    public LastSlotDto() {}

    public LastSlotDto(Short pos) {
        super(pos);
    }

    public int getScore() {
        return isEmpty() ? 0 : super.getScore() + LAST_SLOT_BONUS;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof LastSlotDto && super.equals(o);
    }
}