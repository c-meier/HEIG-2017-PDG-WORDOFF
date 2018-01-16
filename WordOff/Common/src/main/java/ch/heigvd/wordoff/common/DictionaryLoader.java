/*
 * File: DictionaryLoader.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

/**
 * DictionaryLoader loads dictionaries into mememory for a given name.
 * A given instance of DictionaryLoader won't load a dictionary if it has already been loaded.
 */
public class DictionaryLoader {
    private Map<String, Dictionary> loadedDico = new TreeMap<>();

    /**
     * Load the file "lang".txt into memory and makes a Dictionary
     * @param lang
     */
    public void loadDictionary(String lang) {
        if (!loadedDico.containsKey(lang)) {
            lang = lang.toLowerCase();
            try {
                String platformIndependentPath = Paths.get(
                        getClass().getResource("/dictionary/" + lang + ".txt").toURI()).toString();
                loadedDico.put(lang, new Dictionary(platformIndependentPath));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the loaded Dictionary corresponding to the file "lang".txt
     * @param lang
     * @return dictionnaire
     */
    public Dictionary getDico(String lang) {
        loadDictionary(lang);
        return loadedDico.get(lang);
    }
}
