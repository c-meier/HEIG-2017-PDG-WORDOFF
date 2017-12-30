package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.common.Dictionary;
import ch.heigvd.wordoff.common.DictionaryLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Project : WordOff
 * Date : 27.09.17
 */
public class DictionaryTest {
    static Dictionary dicoFr;
    static Dictionary dicoEng;

    @BeforeClass
    public static void init() {
        dicoFr = new DictionaryLoader().getDico("fr");
        dicoEng = new DictionaryLoader().getDico("en");
    }

    @Test
    public void tryingAFewWords() {
        assertTrue(dicoEng.contains("helLo"));
        assertTrue(dicoEng.contains("dad"));
        assertTrue(dicoEng.contains("mummy"));

        assertTrue(dicoFr.contains("cheni"));
    }

    @Test
    public void generateAnagrams() {
        System.out.println(dicoFr.getAnagrams("pap#").toString());
        assertTrue(true);
    }
}
