package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.BadRequestException;
import ch.heigvd.wordoff.client.Exception.TokenNotFoundException;
import ch.heigvd.wordoff.client.Exception.UnauthorizedException;
import ch.heigvd.wordoff.client.Exception.UnprocessableEntityException;
import ch.heigvd.wordoff.client.Util.TokenManager;
import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceList;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceWriteList;
import ch.heigvd.wordoff.common.Dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import static ch.heigvd.wordoff.common.Constants.AUTHORIZATION_HEADER;
import static ch.heigvd.wordoff.common.Constants.SERVER_URI;

class Api {
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();
    private final static Logger LOG = Logger.getLogger(Api.class.getSimpleName());

    private static Api instance = null;

    private static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    static RestTemplate getRestTemplate() {
        return getInstance().restTemplate;
    }

    private Api() {
        this.restTemplate = new RestTemplate();
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
                        LOG.warning(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(err));
                        throw new UnprocessableEntityException(err.getErrorCode(), err.getMsg());
                    default:
                        LOG.severe(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(clientHttpResponse.getBody())));
                        throw new HTTPException(clientHttpResponse.getStatusCode().value());
                }
            }
        });
    }

    static public <T> T get(IResource<T> endpoint) throws TokenNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, TokenManager.loadToken());

        ResponseEntity<T> responseEntity =
                Api.getRestTemplate().exchange(SERVER_URI + endpoint.getEndpoint(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        endpoint.getResourceType());

        return responseEntity.getBody();
    }

    static public <T> T put(IResource<T> endpoint) throws TokenNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, TokenManager.loadToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<T> responseEntity =
                Api.getRestTemplate().exchange(SERVER_URI + endpoint.getEndpoint(),
                        HttpMethod.PUT,
                        new HttpEntity<>((T)endpoint, headers),
                        endpoint.getResourceType());

        return responseEntity.getBody();
    }

    static public <T> List<T> get(ResourceList<T> endpoint) throws TokenNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, TokenManager.loadToken());

        ParameterizedType parameterizedType = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{endpoint.getResponseType()};
            }

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };

        ResponseEntity<List<T>> responseEntity =
                Api.getRestTemplate().exchange(SERVER_URI + endpoint.getEndpoint(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        ParameterizedTypeReference.forType(parameterizedType));

        return responseEntity.getBody();
    }

    static public <T, U> T post(ResourceWriteList<T, U> endpoint, U dto) throws TokenNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, TokenManager.loadToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<T> responseEntity =
                Api.getRestTemplate().exchange(SERVER_URI + endpoint.getEndpoint(),
                        HttpMethod.POST,
                        new HttpEntity<>(dto, headers),
                        endpoint.getResponseType());

        return responseEntity.getBody();
    }
}
