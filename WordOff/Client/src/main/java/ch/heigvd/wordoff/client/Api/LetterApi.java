/*
 * File: LetterApi.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

/**
 * Api used to get the letters of a language.
 */
public class LetterApi {

    private final static RestTemplate restTemplate = Api.getRestTemplate();

    /**
     * Retrieve the letters of a specific language
     * @param lang The language
     * @return The letters
     */
    public static List<Character> retrieveLetters(String lang) {
        final String uri = SERVER_URI + "/letters/{lang}";

        Map<String, String> params = new HashMap<>();
        params.put("lang", lang);

        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<List<Character>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<Character>>() {},
                        params);

        return responseEntity.getBody();
    }
}
