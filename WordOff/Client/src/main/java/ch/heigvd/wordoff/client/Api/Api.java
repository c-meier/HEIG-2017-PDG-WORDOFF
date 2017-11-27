package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.client.Exception.BadRequestException;
import ch.heigvd.wordoff.client.Exception.UnauthorizedException;
import ch.heigvd.wordoff.client.Exception.UnprocessableEntityException;
import ch.heigvd.wordoff.common.Dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.logging.Logger;

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
}
