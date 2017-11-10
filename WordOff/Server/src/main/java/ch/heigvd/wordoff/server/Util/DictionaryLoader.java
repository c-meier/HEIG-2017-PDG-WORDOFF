package ch.heigvd.wordoff.server.Util;

import ch.heigvd.wordoff.common.Dictionary;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

@Component
public class DictionaryLoader {
    private Map<String, Dictionary> loadedDico = new TreeMap<>();

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

    public Dictionary getDico(String lang) {
        loadDictionary(lang);
        return loadedDico.get(lang);
    }
}
