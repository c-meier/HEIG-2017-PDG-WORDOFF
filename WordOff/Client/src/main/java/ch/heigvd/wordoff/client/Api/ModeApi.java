package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.ModeSummaryDto;
import ch.heigvd.wordoff.common.Dto.MessageDto;
import ch.heigvd.wordoff.common.Dto.ModeDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.heigvd.wordoff.common.Constants.AUTHORIZATION_HEADER;
import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

public class ModeApi {

    private final static RestTemplate restTemplate = Api.getRestTemplate();

    public static List<ModeSummaryDto> retrieveModes() throws TokenNotFoundException {
        return retrieveModes(TokenManager.loadToken());
    }

    public static ModeSummaryDto createMode(ModeDto modeDto) throws TokenNotFoundException {
        return createMode(TokenManager.loadToken(), modeDto);
    }

    public static ModeDto getStateMode(int modeId) throws TokenNotFoundException {
        return getStateMode(TokenManager.loadToken(), modeId);
    }

    public static List<MessageDto> getMessageOfMode(int modeId) throws TokenNotFoundException {
        return getMessageOfMode(TokenManager.loadToken(), modeId);
    }

    public static MessageDto sendMessageToMode(int modeId, MessageDto messageDto) throws TokenNotFoundException {
        return sendMessageToMode(TokenManager.loadToken(), modeId, messageDto);
    }

    private static List<ModeSummaryDto> retrieveModes(String token) {
        final String uri = SERVER_URI + "/modes";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<List<ModeSummaryDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<ModeSummaryDto>>() {});

        return responseEntity.getBody();
    }

    private static ModeSummaryDto createMode(String token, ModeDto modeDto) {
        final String uri = SERVER_URI + "/modes";

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ModeSummaryDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(modeDto, headers),
                        ModeSummaryDto.class);

        return responseEntity.getBody();
    }

    private static ModeDto getStateMode(String token, int modeId) {
        final String uri = SERVER_URI + "/modes/{modeId}";

        Map<String, String> params = new HashMap<>();
        params.put("modeId", String.valueOf(modeId));

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<ModeDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        ModeDto.class,
                        params);

        return responseEntity.getBody();
    }

    private static List<MessageDto> getMessageOfMode(String token, int modeId) {
        final String uri = SERVER_URI + "/modes/{modeId}/messages";

        Map<String, String> params = new HashMap<>();
        params.put("modeId", String.valueOf(modeId));

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);

        ResponseEntity<List<MessageDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<MessageDto>>() {},
                        params);

        return responseEntity.getBody();
    }

    private static MessageDto sendMessageToMode(String token, int modeId, MessageDto messageDto) {
        final String uri = SERVER_URI + "/modes/{modeId}/messages";

        Map<String, String> params = new HashMap<>();
        params.put("modeId", String.valueOf(modeId));

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<MessageDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(messageDto, headers),
                        MessageDto.class,
                        params);

        return responseEntity.getBody();
    }
}
