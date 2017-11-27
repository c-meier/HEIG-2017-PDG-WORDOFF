package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.InvitationDto;
import ch.heigvd.wordoff.common.Dto.NotificationDto;
import ch.heigvd.wordoff.common.Dto.User.MeDto;
import ch.heigvd.wordoff.common.Dto.User.RelatedUserSummaryDto;
import ch.heigvd.wordoff.common.Dto.User.RelationDto;
import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.heigvd.wordoff.common.Constants.AUTHORIZATION_HEADER;
import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

public class MeApi {

    private final static RestTemplate restTemplate = Api.getRestTemplate();

    public static MeDto getCurrentUser() throws TokenNotFoundException {
        return getCurrentUser(TokenManager.loadToken());
    }

    public static MeDto updateCurrentUser(MeDto meDto) throws TokenNotFoundException {
        return updateCurrentUser(TokenManager.loadToken(), meDto);
    }

    public static List<NotificationDto> getNotifications() throws TokenNotFoundException {
        return getNotifications(TokenManager.loadToken());
    }

    public static List<InvitationDto> getInvitations() throws TokenNotFoundException {
        return getInvitations(TokenManager.loadToken());
    }

    public static List<UserSummaryDto> getAdversaries() throws TokenNotFoundException {
        return getAdversaries(TokenManager.loadToken());
    }

    public static List<RelatedUserSummaryDto> getRelations() throws TokenNotFoundException {
        return getRelations(TokenManager.loadToken());
    }

    public static RelationDto updateRelation(int userId, RelationDto relationDto) throws TokenNotFoundException {
        return updateRelation(TokenManager.loadToken(), userId, relationDto);
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

    private static MeDto updateCurrentUser(String token, MeDto meDto) {
        final String uri = SERVER_URI + "/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<MeDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.PUT,
                        new HttpEntity<>(meDto, headers),
                        MeDto.class);

        return responseEntity.getBody();
    }

    private static List<NotificationDto> getNotifications(String token) {
        final String uri = SERVER_URI + "/me/notifications";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<List<NotificationDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<NotificationDto>>() {});

        return responseEntity.getBody();
    }

    private static List<InvitationDto> getInvitations(String token) {
        final String uri = SERVER_URI + "/me/invitations";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<List<InvitationDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<InvitationDto>>() {});

        return responseEntity.getBody();
    }

    private static List<UserSummaryDto> getAdversaries(String token) {
        final String uri = SERVER_URI + "/me/adversaries";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<List<UserSummaryDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<UserSummaryDto>>() {});

        return responseEntity.getBody();
    }

    private static List<RelatedUserSummaryDto> getRelations(String token) {
        final String uri = SERVER_URI + "/me/relations";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<List<RelatedUserSummaryDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<RelatedUserSummaryDto>>() {});

        return responseEntity.getBody();
    }

    private static RelationDto updateRelation(String token, int userId, RelationDto relationDto) {
        final String uri = SERVER_URI + "/me/relations/{userId}";

        Map<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(userId));

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<RelationDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.PUT,
                        new HttpEntity<>(relationDto, headers),
                        RelationDto.class,
                        params);

        return responseEntity.getBody();
    }
}
