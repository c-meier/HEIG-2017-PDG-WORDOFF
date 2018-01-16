/*
 * File: GameApi.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.Game.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.GameSummaryDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.heigvd.wordoff.common.Constants.AUTHORIZATION_HEADER;
import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

/**
 * Api that holds the calls to handle a game.
 */
public class GameApi {

    private final static RestTemplate restTemplate = Api.getRestTemplate();

    /**
     * Retrieve the list of games of the user
     * @return The games
     * @throws TokenNotFoundException If the user is not logged in
     */
    public static List<GameSummaryDto> retrieveGames() throws TokenNotFoundException {
        return retrieveGames(TokenManager.loadToken());
    }

    /**
     * Create a new game
     * @param lang The language of the game
     * @param playerIds The player IDs
     * @return A summary of the game created
     * @throws TokenNotFoundException If the user is not logged in
     */
    public static GameSummaryDto createGame(String lang, List<Long> playerIds) throws TokenNotFoundException {
        return createGame(TokenManager.loadToken(), lang, playerIds);
    }

    /**
     * Retrieve a specific game
     * @param gameId The id of the game wanted
     * @return The game
     * @throws TokenNotFoundException If the user is not logged in
     */
    public static GameDto getGame(Long gameId) throws TokenNotFoundException {
        return getGame(TokenManager.loadToken(), gameId);
    }

    /**
     * Play a challenge for a game
     * @param gameId The game ID
     * @param challengeDto The challenge played
     * @return The game new state
     * @throws TokenNotFoundException If the user is not logged in
     */
    public static GameDto play(Long gameId, ChallengeDto challengeDto) throws TokenNotFoundException {
        return play(TokenManager.loadToken(), gameId, challengeDto);
    }

    private static List<GameSummaryDto> retrieveGames(String token) {
        final String uri = SERVER_URI + "/games";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<List<GameSummaryDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<GameSummaryDto>>() {
                        });

        return responseEntity.getBody();
    }

    private static GameSummaryDto createGame(String token, String lang, List<Long> playerIds) {
        final String uri = SERVER_URI + "/games";

        Map<String, String> params = new HashMap<>();
        params.put("lang", lang);

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<GameSummaryDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(playerIds, headers),
                        GameSummaryDto.class);

        return responseEntity.getBody();
    }

    private static GameDto getGame(String token, Long gameId) {
        final String uri = SERVER_URI + "/games/{gameId}";

        Map<String, String> params = new HashMap<>();
        params.put("gameId", gameId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<GameDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        GameDto.class,
                        params);

        return responseEntity.getBody();
    }

    private static GameDto play(String token, Long gameId, ChallengeDto challengeDto) {
        final String uri = SERVER_URI + "/games/{gameId}/challenge";

        Map<String, String> params = new HashMap<>();
        params.put("gameId", gameId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<GameDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(challengeDto, headers),
                        GameDto.class,
                        params);

        return responseEntity.getBody();
    }
}
