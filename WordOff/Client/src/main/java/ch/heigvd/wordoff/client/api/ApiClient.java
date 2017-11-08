package ch.heigvd.wordoff.client.api;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dto.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.GameDto;
import ch.heigvd.wordoff.common.Dto.LoginDto;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Luthier
 */
public class ApiClient {

    private final static String host = "http://" + Constants.SERVER_ADDRESS + ":" + Constants.SERVER_PORT;
    private final static RestTemplate restTemplate = new RestTemplate();

    public static GameDto play(String token, Long gameId, ChallengeDto challengeDto) {
        final String uri = host + "/game/{gameId}/challenge";

        Map<String, String> params = new HashMap<>();
        params.put("gameId", gameId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        ResponseEntity responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(challengeDto, headers),
                        ResponseEntity.class,
                        params);
        GameDto gameDto = (GameDto) responseEntity.getBody();

        return gameDto;
    }

    public static GameDto getGame(Long gameId) {
        final String uri = host + "/game/{gameId}/getGame";

        Map<String, String> params = new HashMap<>();
        params.put("gameId", gameId.toString());

        ResponseEntity responseEntityGameDto = restTemplate.getForObject(uri, ResponseEntity.class, params);
        GameDto gameDto = (GameDto) responseEntityGameDto.getBody();

        return gameDto;
    }

    public static String signIn(LoginDto loginDto) {
        final String uri = host + "/users/sign-in";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(loginDto, headers),
                        ResponseEntity.class);

        String token = "";

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            HttpHeaders httpHeaders = responseEntity.getHeaders();
            List<String> test = httpHeaders.get("Authorization");
            token = (String) responseEntity.getBody();
        }

        return token;
    }

    public static boolean signUp(LoginDto loginDto) {
        final String uri = host + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(loginDto, headers),
                        ResponseEntity.class);

        return responseEntity.getStatusCode().equals(HttpStatus.CREATED);
    }
}
