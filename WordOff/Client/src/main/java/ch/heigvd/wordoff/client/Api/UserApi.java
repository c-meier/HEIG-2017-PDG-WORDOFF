package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.BadRequestException;
import ch.heigvd.wordoff.client.Exception.UnauthorizedException;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.ErrorDto;
import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.User.LoginDto;
import ch.heigvd.wordoff.common.Dto.User.UserDto;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

public class UserApi {

    private final static RestTemplate restTemplate = Api.getRestTemplate();

    public static void signUp(LoginDto loginDto) {
        final String uri = SERVER_URI + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(uri,
                HttpMethod.POST,
                new HttpEntity<>(loginDto, headers),
                ResponseEntity.class);
    }

    public static MeDto signIn(LoginDto loginDto) {
        final String uri = SERVER_URI + "/users/sign-in";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<MeDto> responseEntity = null;
        try {
            responseEntity =
                    restTemplate.exchange(uri,
                            HttpMethod.POST,
                            new HttpEntity<>(loginDto, headers),
                            MeDto.class);
        } catch (HttpClientErrorException e) {
            switch (e.getStatusCode()) {
                case BAD_REQUEST: // 400
                    throw new BadRequestException();
                case UNAUTHORIZED: // 401
                    throw new UnauthorizedException();
                default:
                    throw new HTTPException(responseEntity.getStatusCode().value());
            }
        }

        // retrieve token from header
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        List<String> listHeaders = httpHeaders.get("Authorization");
        String token = listHeaders.get(0);
        // save token to filesystem
        TokenManager.saveToken(token);

        return responseEntity.getBody();
    }

    public static UserDto getProfile(int userId) {
        final String uri = SERVER_URI + "/user/{userId}";

        Map<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(userId));

        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<UserDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        UserDto.class,
                        params);

        return responseEntity.getBody();
    }

}
