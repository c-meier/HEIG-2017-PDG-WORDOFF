import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Project : WordOff
 * Author(s) : Antoine Friant
 * Date : 27.09.17
 */
public class TSTTest {
    @Test
    public void mainTest() {
        TST tst = new TST();
        assertTrue(tst.isEmpty());

        tst.insert("Ceci est un mot");
        tst.insert("Ceci est un autre mot");
        tst.insert("banana");
        tst.insert("\'kay");

        assertFalse(tst.isEmpty());
        assertTrue(tst.search("\'kay"));
        assertFalse(tst.search("\'ka"));
        assertFalse(tst.search("\'kayy"));
        assertTrue(tst.search("Ceci est un mot"));

        tst.clear();
        assertTrue(tst.isEmpty());
        assertFalse(tst.search("\'kay"));
    }
}