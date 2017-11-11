package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.BadRequestException;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Exception.UnauthorizedException;
import ch.heigvd.wordoff.client.Exception.UnprocessableEntityException;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.ErrorDto;
import ch.heigvd.wordoff.common.Dto.GameDto;
import ch.heigvd.wordoff.common.Dto.GameSummaryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.heigvd.wordoff.common.Constants.AUTHORIZATION_HEADER;
import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

public class GameApi {

    private final static RestTemplate restTemplate = new RestTemplate();
    private final static ObjectMapper mapper = new ObjectMapper();

    {
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                HttpStatus status = clientHttpResponse.getStatusCode();
                return status.is4xxClientError() || status.is5xxServerError();
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                switch (clientHttpResponse.getStatusCode()) {
                    case BAD_REQUEST: // 400
                        throw new BadRequestException();
                    case UNAUTHORIZED: // 401
                        throw new UnauthorizedException();
                    case UNPROCESSABLE_ENTITY: // 422
                        ErrorDto err = mapper.readValue(clientHttpResponse.getBody(), ErrorDto.class);
                        throw new UnprocessableEntityException(err.getErrorCode(), err.getMsg());
                    default:
                        throw new HTTPException(clientHttpResponse.getStatusCode().value());
                }
            }
        });
    }

    public static List<GameSummaryDto> retrieveGames() throws TokenNotFoundException {
        return retrieveGames(TokenManager.loadToken());
    }

    public static GameSummaryDto createGame(String lang, List<Long> playerIds) throws TokenNotFoundException {
        return createGame(TokenManager.loadToken(), lang, playerIds);
    }

    public static  GameDto getGame(Long gameId) throws TokenNotFoundException {
        return getGame(TokenManager.loadToken(), gameId);
    }

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
                        new ParameterizedTypeReference<List<GameSummaryDto>>() {});

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
