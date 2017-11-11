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
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.heigvd.wordoff.common.Constants.AUTHORIZATION_HEADER;
import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

public class GameApi {

    private final static RestTemplate restTemplate = new RestTemplate();

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

        ResponseEntity responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(headers),
                        ResponseEntity.class);

        switch (responseEntity.getStatusCode()) {
            case OK: // 200
                return (List<GameSummaryDto>)responseEntity.getBody();
            case UNAUTHORIZED: // 401
                throw new UnauthorizedException();
            default:
                throw new HTTPException(responseEntity.getStatusCode().value());
        }
    }

    private static GameSummaryDto createGame(String token, String lang, List<Long> playerIds) {
        final String uri = SERVER_URI + "/games";

        Map<String, String> params = new HashMap<>();
        params.put("lang", lang);

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(playerIds, headers),
                        ResponseEntity.class);

        switch (responseEntity.getStatusCode()) {
            case CREATED: // 201
                return (GameSummaryDto)responseEntity.getBody();
            case BAD_REQUEST: // 400
                throw new BadRequestException();
            case UNPROCESSABLE_ENTITY: // 422
                ErrorDto err = (ErrorDto)responseEntity.getBody();
                throw new UnprocessableEntityException(err.getErrorCode(), err.getMsg());
            default:
                throw new HTTPException(responseEntity.getStatusCode().value());
        }
    }

    private static GameDto getGame(String token, Long gameId) {
        final String uri = SERVER_URI + "/games/{gameId}";

        Map<String, String> params = new HashMap<>();
        params.put("gameId", gameId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        ResponseEntity.class,
                        params);

        switch (responseEntity.getStatusCode()) {
            case OK: // 200
                return (GameDto)responseEntity.getBody();
            case UNAUTHORIZED: // 401
                throw new UnauthorizedException();
            case  UNPROCESSABLE_ENTITY: // 422
                ErrorDto err = (ErrorDto)responseEntity.getBody();
                throw new UnprocessableEntityException(err.getErrorCode(), err.getMsg());
            default:
                throw new HTTPException(responseEntity.getStatusCode().value());
        }
    }

    private static GameDto play(String token, Long gameId, ChallengeDto challengeDto) {
        final String uri = SERVER_URI + "/games/{gameId}/challenge";

        Map<String, String> params = new HashMap<>();
        params.put("gameId", gameId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(challengeDto, headers),
                        ResponseEntity.class,
                        params);

        switch (responseEntity.getStatusCode()) {
            case OK: // 200
                return (GameDto)responseEntity.getBody();
            case BAD_REQUEST: // 400
                throw new BadRequestException();
            case UNAUTHORIZED: // 401
                throw new UnauthorizedException();
            case  UNPROCESSABLE_ENTITY: // 422
                ErrorDto err = (ErrorDto)responseEntity.getBody();
                throw new UnprocessableEntityException(err.getErrorCode(), err.getMsg());
            default:
                throw new HTTPException(responseEntity.getStatusCode().value());
        }
    }
}
