package ch.heigvd.wordoff.common.Model.Slots;

/**
 * Created by Daniel on 19.10.2017.
 */
public enum SlotType {
    BASIC(0),
    L2SLOT(1),
    L3SLOT(2),
    SWAP(3),
    SEVENTH(4);

    private final Integer value;

    private SlotType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
