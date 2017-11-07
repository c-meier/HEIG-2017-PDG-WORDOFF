package ch.heigvd.wordoff.common;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dictionary;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Project : WordOff
 * Date : 27.09.17
 */
public class DictionaryTest {
    static Dictionary dicoFr;
    static Dictionary dicoEng;

    @BeforeClass
    public static void init() {
        dicoFr = new Dictionary(Constants.FRENCH_DICTIONARY);
        dicoEng = new Dictionary(Constants.ENGLISH_DICTIONARY);
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
        System.out.println(dicoFr.getAnagrams("am#").toString());
        assertTrue(true);
    }
}
