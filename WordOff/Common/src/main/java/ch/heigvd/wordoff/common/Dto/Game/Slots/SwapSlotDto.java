/*
 * File: SwapSlotDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.Game.Slots;

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
