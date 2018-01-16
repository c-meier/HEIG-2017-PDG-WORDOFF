/*
 * File: Dictionary.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Dictionary describes a dictionary loaded in a TST capable of generating anagrams
 * and efficiently looking up words.
 */
@Immutable
public class Dictionary {
    private TST tst;

    /**
     * Initialize a dictionary from a file
     * The file must contain one word per line
     * It must be encoded in UTF-8
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
                    // words must have at least 2 characters to be valid in WordOff
                    tst.insert(w);
                }
            });

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * @param str
     * @return true if and only if the dictionary contains str
     */
    public boolean contains(String str) {
        return tst.contains(str.toUpperCase());
    }

    /**
     * @param str
     * @return a list of correct words that can be written only using the letters in str
     */
    public List<String> getAnagrams(String str) {
        return tst.getAnagrams(str.toUpperCase());
    }
}
