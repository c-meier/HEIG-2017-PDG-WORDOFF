package ch.heigvd.wordoff.client.api;

import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;

/**
 *
 * @author Gabriel Luthier
 */
public class ApiClientHttpRequestFactory implements ClientHttpRequestFactory {

    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod hm) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}