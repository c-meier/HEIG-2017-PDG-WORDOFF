package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.User.LoginDto;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
        ResponseEntity<MeDto> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(loginDto, headers),
                        MeDto.class);

        // retrieve token from header
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        List<String> listHeaders = httpHeaders.get("Authorization");
        String token = listHeaders.get(0);
        // save token to filesystem
        TokenManager.saveToken(token);

        return responseEntity.getBody();
    }

}