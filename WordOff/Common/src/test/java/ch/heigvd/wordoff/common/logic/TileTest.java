package ch.heigvd.wordoff.common.logic;

import ch.heigvd.wordoff.common.Model.Tiles.Tile;
import org.junit.*;
import static org.junit.Assert.*;

public class TileTest {
    static Tile tile1;
    static Tile tile2;

    @BeforeClass
    public static void init(){
        tile1 = new Tile(0,'c',1);
        tile2 = new Tile(5,'z',10);
    }

    @Test
    public void testGetValue(){
        assertEquals('c',tile1.getValue());
        assertEquals('z',tile2.getValue());
    }

    @Test
    public void testGetScore(){
        assertEquals(1,tile1.getScore());
        assertEquals(10,tile2.getScore());
    }

    @Test
    public void testGetId(){
        assertEquals(0,tile1.getId());
        assertEquals(5,tile2.getId());
    }
}
