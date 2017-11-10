package ch.heigvd.wordoff.common;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Project : WordOff
 * Date : 27.09.17
 */
@Immutable
public class Dictionary {
    private TST tst;

    /**
     * Initialise un dictionaire à partir du fichier en argument
     * Il doit contenir un mot par ligne
     * Il doit être en UTF-8
     *
     * @param file
     */
    public Dictionary(String file) {
        tst = new TST();
        try (Stream<String> stream = Files.lines(Paths.get(file), Charset.forName("UTF-8"))) {
            stream.forEach(w -> {
                w = w.toUpperCase();
                w = java.text.Normalizer.normalize(w, java.text.Normalizer.Form.NFD)
                        .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                        .replaceAll("[^A-Za-z]", "");

                if (w.length() > 1) {
                    // les mots doivent avoir plus d'une lettre pour être joués dans WordOff
                    tst.insert(w);
                }
            });

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * @param str
     * @return vrai ssi str se trouve dans le dictionaire
     */
    public boolean contains(String str) {
        return tst.contains(str.toUpperCase());
    }

    /**
     * @param str
     * @return une liste de mots formés des lettres de str se trouvant dans le dictionaire
     */
    public List<String> getAnagrams(String str) {
        return tst.getAnagrams(str.toUpperCase());
    }
}
