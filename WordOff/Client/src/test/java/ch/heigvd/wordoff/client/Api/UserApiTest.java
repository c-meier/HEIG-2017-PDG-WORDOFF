package ch.heigvd.wordoff.client.Api;

import ch.heigvd.wordoff.common.Dto.User.LoginDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static ch.heigvd.wordoff.common.Constants.SERVER_URI;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class UserApiTest {
    @Autowired
    private UserApi userApi;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        restTemplate = Api.getRestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testSignIn() {
        mockServer.expect(requestTo(SERVER_URI + "/users/sign-in"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andRespond(withSuccess());

        UserApi.signIn(new LoginDto("one", "pass".toCharArray()));

        mockServer.verify();
    }
}
