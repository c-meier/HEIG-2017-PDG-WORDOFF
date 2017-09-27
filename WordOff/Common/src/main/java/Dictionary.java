import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Project : WordOff
 * Date : 27.09.17
 */
public class Dictionary {
    private TST tst;

    /**
     * Initialise un dictionaire à partir du fichier en argument
     * Il doit contenir un mot par ligne
     * Il doit être en UTF-8
     *
     * @param fileName
     */
    public Dictionary(String fileName) {
        tst = new TST();
        try (Stream<String> stream = Files.lines(Paths.get(fileName), Charset.forName("UTF-8"))) {
            stream.forEach(w -> {
                w = w.toUpperCase();
                w = java.text.Normalizer.normalize(w, java.text.Normalizer.Form.NFD)
                        .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                        .replaceAll("[^A-Za-z]", "");

                if (w.length() > 1) {
                    tst.insert(w);
                }
            });

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public boolean contains(String word) {
        String w = word.toUpperCase();

        return tst.contains(w);
    }
}
