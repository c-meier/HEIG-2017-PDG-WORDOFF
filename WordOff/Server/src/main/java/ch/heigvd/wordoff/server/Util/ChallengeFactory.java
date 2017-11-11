package ch.heigvd.wordoff.server.Util;

import ch.heigvd.wordoff.common.IModel.ISlot;
import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Side;
import ch.heigvd.wordoff.server.Model.Slots.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ChallengeFactory {
    List<ISlot> slots;

    Side side;

    public ChallengeFactory(Side side) {
        this.side = side;
        this.slots = new ArrayList<>();
    }

    public ChallengeFactory add(Class<? extends Slot> slotClass) {
        try {
            Constructor<? extends Slot> ctor = slotClass.getConstructor(Short.class);
            Slot slot = ctor.newInstance((short)(slots.size() + 1));
            slots.add(slot);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return this;
    }

    public ChallengeFactory addAll(List<Class<? extends Slot>> slotsClass) {
        for(Class<? extends Slot> slotClass : slotsClass) {
            add(slotClass);
        }
        return this;
    }

    public ChallengeFactory createRandomSlotPos() {
        // List of the slots without the last slots and the bonus slots
        List<Class<? extends Slot>> slots = new ArrayList<>(Arrays.asList(Slot.class, Slot.class, Slot.class, SwapSlot.class, SwapSlot.class));
        // List of the bonus slots
        List<Class<? extends Slot>> lxSlots = Arrays.asList(L2Slot.class, L3Slot.class, Slot.class);
        Random random = new Random();
        int index = random.nextInt(lxSlots.size());
        // add one of the bonus slots to the slots list
        slots.add(lxSlots.get(index));
        // shuffle the list of slots
        Collections.shuffle(slots);
        // Add the 7th slot
        slots.add(LastSlot.class);
        return addAll(slots);
    }

    public Challenge create() {
        return new Challenge(slots);
    }
}
