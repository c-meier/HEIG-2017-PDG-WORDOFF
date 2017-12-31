package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.BadRequestException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

public class LetterApi {

    private final static RestTemplate restTemplate = new RestTemplate();

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

        switch (responseEntity.getStatusCode()) {
            case OK: // 200
                return (List<Character>)responseEntity.getBody();
            case BAD_REQUEST: // 400
                throw new BadRequestException();
            default:
                throw new HTTPException(responseEntity.getStatusCode().value());
        }
    }
}
