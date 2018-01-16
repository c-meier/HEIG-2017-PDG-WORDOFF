/*
 * File: UserApiTest.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Api;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

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
/*
    @Test
    public void testSignIn() {
        mockServer.expect(requestTo(SERVER_URI + "/users/sign-in"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andRespond(withSuccess());

        UserApi.signIn(new LoginDto("one", "pass".toCharArray()));

        mockServer.verify();
    }*/
}
