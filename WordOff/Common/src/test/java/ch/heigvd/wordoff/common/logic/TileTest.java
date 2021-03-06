/*
 * File: TileTest.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.logic;

import ch.heigvd.wordoff.common.Dto.Game.Tiles.TileDto;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileTest {
    static TileDto tile1;
    static TileDto tile2;

    @BeforeClass
    public static void init(){
        tile1 = new TileDto(0,'c',1);
        tile2 = new TileDto(5,'z',10);
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
