/*
 * File: L2SlotDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.Game.Slots;

public class L2SlotDto extends LxSlotDto {

    private static int bonus = 2;

    // Necessary for Jackson deserialization
    public L2SlotDto() {
        super(bonus);
    }

    public L2SlotDto(Short pos) {
        super(pos, bonus);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof L2SlotDto && super.equals(o);
    }
}
