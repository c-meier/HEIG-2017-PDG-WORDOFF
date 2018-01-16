/*
 * File: Api.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

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

/**
 * Class that holds the instance of a RestTemplate used to query the server.
 * Provides also methods to follow up the queries based on endpoints returned by the server.
 */
public class Api {
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

    /**
     * Let the others classes of the api use the RestTemplate
     * @return The RestTemplate
     */
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
                        throw new UnprocessableEntityException(err.getErrorCode(), err.getMsg());
                    default:
                        throw new HTTPException(clientHttpResponse.getStatusCode().value());
                }
            }
        });
    }

    /**
     * Do a GET query on a endpoint to retrieve a single object
     * @param endpoint The endpoint
     * @param <T> The type of the objet wanted
     * @return The object
     * @throws TokenNotFoundException If the user is not logged in
     */
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

    /**
     * Do a PUT query on a endpoint to update an object
     * @param endpoint The endpoint
     * @param <T> The type of the object updated
     * @return The object
     * @throws TokenNotFoundException If the user is not logged in
     */
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

    /**
     * Do a GET query on a endpoint to get a list of objects
     * @param endpoint The endpoing
     * @param <T> The type of the objects wanted
     * @return The list of objects
     * @throws TokenNotFoundException If the user is not logged in
     */
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

    /**
     * Do a POST query on a endpoint
     * @param endpoint The endpoint
     * @param dto The DTO of the object posted
     * @param <T> The typoe of the object returned
     * @param <U> The type of the object posted
     * @return The object returned by the server
     * @throws TokenNotFoundException If the user is not logged in
     */
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

    /**
     * Do a POST on a endpoint
     * @param endpoint The endpoint
     * @param dto The DTO of the object posted
     * @param respType The Class of the object returned
     * @param <T> The type of the object returned
     * @param <U> The type of the object posted
     * @return The objet returned by the server
     * @throws TokenNotFoundException If the user is not logged in
     */
    static public <T, U> T post(ResourceWriteList<T, U> endpoint, U dto, Class<T> respType) throws TokenNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, TokenManager.loadToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<T> responseEntity =
                Api.getRestTemplate().exchange(SERVER_URI + endpoint.getEndpoint(),
                        HttpMethod.POST,
                        new HttpEntity<>(dto, headers),
                        respType);

        return responseEntity.getBody();
    }
}
