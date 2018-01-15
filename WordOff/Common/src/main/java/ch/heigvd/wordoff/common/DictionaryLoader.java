package ch.heigvd.wordoff.common;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe permettant de charger les dictionnaires en mémoire à partir de leur nom
 * Une instance de DictionaryLoader ne chargera pas inutilement un dictionnaire qu'elle a déjà chargé.
 */
public class DictionaryLoader {
    private Map<String, Dictionary> loadedDico = new TreeMap<>();

    /**
     * Charge en mémoire le dictionnaire du fichier "lang".txt
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
     * Récupère le dictionnaire correspondant au nom de fichier "lang".txt
     * @param lang
     * @return dictionnaire
     */
    public Dictionary getDico(String lang) {
        loadDictionary(lang);
        return loadedDico.get(lang);
    }
}
