/*
 * File: MeApi.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.MeDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static ch.heigvd.wordoff.common.Constants.AUTHORIZATION_HEADER;
import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

/**
 * Api used to get the current user.
 */
public class MeApi {

    private final static RestTemplate restTemplate = Api.getRestTemplate();

    /**
     * Retrieve the current user
     * @return The current use
     * @throws TokenNotFoundException If the user is not logged in
     */
    public static MeDto getCurrentUser() throws TokenNotFoundException {
        return getCurrentUser(TokenManager.loadToken());
    }

    private static MeDto getCurrentUser(String token) {
        final String uri = SERVER_URI + "/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<MeDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        MeDto.class);

        return responseEntity.getBody();
    }
}
