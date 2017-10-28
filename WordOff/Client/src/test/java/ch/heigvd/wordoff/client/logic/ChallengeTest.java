package ch.heigvd.wordoff.client.logic;
import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dictionary;
import ch.heigvd.wordoff.common.Model.Slots.L2;
import ch.heigvd.wordoff.common.Model.Slots.SevenTh;
import ch.heigvd.wordoff.common.Model.Slots.Slot;
import ch.heigvd.wordoff.common.Model.Slots.Swap;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import javafx.collections.ObservableList;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;

public class ChallengeTest {
  /*  static ArrayList initSlot;

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

    }

    @Test
    public void testGetSlots() {
        Challenge ch = new Challenge(new Side(), initSlot, new Dictionary(Constants.ENGLISH_DICTIONARY));
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
        Challenge ch = new Challenge(new Side(), initSlot,new Dictionary(Constants.ENGLISH_DICTIONARY));
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
        Challenge ch = new Challenge(new Side(), initSlot, new Dictionary(Constants.ENGLISH_DICTIONARY));
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

    @Test
    public void testGetSizeChallenge(){
        Challenge ch = new Challenge(new Side(), initSlot, new Dictionary(Constants.ENGLISH_DICTIONARY));
        assertEquals(7, ch.getSizeChallenge());
    }
    @Test
    public void testPlayTurn(){
        Challenge ch = new Challenge(new Side(), initSlot, new Dictionary(Constants.ENGLISH_DICTIONARY));
        ObservableList<Slot> chSlots = ch.getSlots();
        chSlots.get(0).addTile(new Tile(0,'h',4));
        chSlots.get(1).addTile(new Tile(1,'e',1)); // Case x2
        chSlots.get(2).addTile(new Tile(2,'l',1));
        chSlots.get(3).addTile(new Tile(3,'l',1));
        chSlots.get(4).addTile(new Tile(4,'o',1));

        assertTrue(ch.playTurn());
        chSlots.get(5).addTile(new Tile(5,'w',10));
        assertFalse(ch.playTurn());
    }*/
}