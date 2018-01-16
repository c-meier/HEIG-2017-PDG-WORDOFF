/*
 * File: L3SlotDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.Game.Slots;

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
