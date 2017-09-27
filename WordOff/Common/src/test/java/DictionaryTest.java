import org.junit.*;

import static org.junit.Assert.*;

/**
 * Project : WordOff
 * Author(s) : Antoine Friant
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
        assertTrue(dicoFr.contains("informatique"));

        assertTrue(dicoFr.contains("sasseoir"));
    }
}
