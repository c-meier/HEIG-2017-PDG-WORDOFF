package ch.heigvd.wordoff.common;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

public class DictionnaryLoader {
    private static DictionnaryLoader dicoListInstance = new DictionnaryLoader();

    public static DictionnaryLoader getInstance() {
        return dicoListInstance;
    }

    private static Map<String, Dictionary> loadedDico = new TreeMap<>();

    public void loadDictionary(String lang) {
        if (!loadedDico.containsKey(lang)) {
            URL path = getClass().getResource(lang + ".txt");
            // Check if exist
            loadedDico.put(lang, new Dictionary(path.toString()));
        }
    }

    public Dictionary getDico(String lang) {
        loadDictionary(lang);
        return loadedDico.get(lang);
    }
}
