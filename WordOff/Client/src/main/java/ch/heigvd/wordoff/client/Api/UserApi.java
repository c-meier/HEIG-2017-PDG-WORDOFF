package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.BadRequestException;
import ch.heigvd.wordoff.client.Exception.UnauthorizedException;
import ch.heigvd.wordoff.client.Exception.UnprocessableEntityException;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.ErrorDto;
import ch.heigvd.wordoff.common.Dto.LoginDto;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.util.List;

import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

public class UserApi {

    private final static RestTemplate restTemplate = new RestTemplate();

    public static void signUp(LoginDto loginDto) {
        final String uri = SERVER_URI + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(loginDto, headers),
                        ResponseEntity.class);

        switch (responseEntity.getStatusCode()) {
            case OK: // 200
                return;
            case BAD_REQUEST: // 400
                throw new BadRequestException();
            case UNAUTHORIZED: // 401
                throw new UnauthorizedException();
            case UNPROCESSABLE_ENTITY: // 422
                ErrorDto err = (ErrorDto) responseEntity.getBody();
                throw new UnprocessableEntityException(err.getErrorCode(), err.getMsg());
            default:
                throw new HTTPException(responseEntity.getStatusCode().value());
        }
    }

    public static void signIn(LoginDto loginDto) {
        final String uri = SERVER_URI + "/users/sign-in";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.POST,
                        new HttpEntity<>(loginDto, headers),
                        ResponseEntity.class);

        switch (responseEntity.getStatusCode()) {
            case OK:
                // retrieve token from header
                HttpHeaders httpHeaders = responseEntity.getHeaders();
                List<String> listHeaders = httpHeaders.get("Authorization");
                String token = listHeaders.get(0);
                // save token to filesystem
                TokenManager.saveToken(token);
                return;
            case BAD_REQUEST: // 400
                throw new BadRequestException();
            case UNAUTHORIZED: // 401
                throw new UnauthorizedException();
            default:
                throw new HTTPException(responseEntity.getStatusCode().value());
        }
    }

}
