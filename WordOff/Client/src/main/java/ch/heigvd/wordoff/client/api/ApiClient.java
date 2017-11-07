package ch.heigvd.wordoff.client.api;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Message.Login;
import ch.heigvd.wordoff.common.Model.Challenge;
import ch.heigvd.wordoff.common.Model.Side;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Gabriel Luthier
 */
public class ApiClient {
    
    private final static ClientHttpRequestFactory clientHttpRequestFactory =
            new ApiClientHttpRequestFactory();
    
    private final static String host = "http://"+Constants.SERVER_ADDRESS+":"+Constants.SERVER_PORT;
    
    public static Side play(Long gameId, Challenge challenge) {
        final String uri = host + "/game/{gameId}/play";
        
        Map<String, String> params = new HashMap<>();
        params.put("gameId", gameId.toString());
        
        RestTemplate restTemplate = new RestTemplate();
        Side resultSide = restTemplate.postForObject(uri, challenge, Side.class, params);
        
        return resultSide;
    }
    
    public static Challenge getAdversary(Long gameId) {
        final String uri = host + "/game/{gameId}/getAdversary";
        
        Map<String, String> params = new HashMap<>();
        params.put("gameId", gameId.toString());
        
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        Challenge resultChallenge = restTemplate.getForObject(uri, Challenge.class, params);
        
        return resultChallenge;
    }
    
    public static void signIn(Login login) {
        final String uri = host + "/user/sign-in";
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, login, null);
    }
    
    public static void signUp(Login login) {
        final String uri = host + "/user/sign-up";
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, login, null);
    }
}
