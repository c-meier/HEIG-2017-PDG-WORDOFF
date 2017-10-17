import ch.heigvd.wordoff.logic.*;

import org.junit.*;
import static org.junit.Assert.*;


import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChallengeTest {
    static ArrayList initSlot;
    static ObservableList slots;

    @BeforeClass
    public static void init() {
        initSlot = new ArrayList();
        initSlot.add(1);
        initSlot.add(2);
        initSlot.add(4);
        initSlot.add(1);
        initSlot.add(1);
        initSlot.add(4);
        initSlot.add(5);
        slots = FXCollections.observableArrayList();
        slots.add(new Slot());
        slots.add(new L2());
        slots.add(new Swap());
        slots.add(new Slot());
        slots.add(new Slot());
        slots.add(new Swap());
        slots.add(new SevenThTest());
    }

    @Test
    public void testGetSlots() {
        Challenge ch = new Challenge(new Side(), initSlot);
        ObservableList<Slot> chSlots = ch.getSlots();
        assertEquals(Slot.class, chSlots.get(0).getClass());
        assertEquals(L2.class, chSlots.get(1).getClass());
        assertEquals(Swap.class, chSlots.get(2).getClass());
        assertEquals(Slot.class, chSlots.get(3).getClass());
        assertEquals(Slot.class, chSlots.get(4).getClass());
        assertEquals(Swap.class, chSlots.get(5).getClass());
        assertEquals(SevenTh.class, chSlots.get(6).getClass());
    }

    @Test
    public void testGetScore(){
        Challenge ch = new Challenge(new Side(), initSlot);
        ObservableList<Slot> chSlots = ch.getSlots();

        chSlots.get(0).addTile(new Tile(0,'h',4));
        chSlots.get(1).addTile(new Tile(1,'e',1)); // Case x2
        chSlots.get(2).addTile(new Tile(2,'l',1));
        chSlots.get(3).addTile(new Tile(3,'l',1));
        chSlots.get(4).addTile(new Tile(4,'o',1));

        assertEquals(9,ch.getScoreWord());

        chSlots.get(5).addTile(new Tile(5,'s',1));

        assertEquals(10,ch.getScoreWord());
    }

    @Test
    public void testCheckWord(){
        Challenge ch = new Challenge(new Side(), initSlot);
        ObservableList<Slot> chSlots = ch.getSlots();
        chSlots.get(0).addTile(new Tile(0,'h',4));
        chSlots.get(1).addTile(new Tile(1,'e',1)); // Case x2
        chSlots.get(2).addTile(new Tile(2,'l',1));
        chSlots.get(3).addTile(new Tile(3,'l',1));
        chSlots.get(4).addTile(new Tile(4,'o',1));

        assertTrue(ch.checkWord());
        chSlots.get(5).addTile(new Tile(5,'w',10));
        assertFalse(ch.checkWord());
    }
}
