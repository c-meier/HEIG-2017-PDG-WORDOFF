package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.server.Model.Credentials;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Rest.Exception.UserAlreadyExistException;
import ch.heigvd.wordoff.server.Security.SecurityConst;
import ch.heigvd.wordoff.common.Model.LoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<String> test() {
        return Arrays.asList("one", "two");
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public void signIn(@RequestBody LoginDto login, HttpServletResponse res) {
        User user = userRepository.findByCredentialsLogin(login.getLogin());
        if(user != null) {
            if(Arrays.equals(user.getCredentials().getPassword(),login.getPassword())) {
                String token = getToken(user);
                res.addHeader(SecurityConst.AUTH_HEADER, SecurityConst.TOKEN_PREFIX + token);
            }
        }
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody LoginDto login, HttpServletResponse res) {
        User user = userRepository.findByCredentialsLogin(login.getLogin());
        if(user == null) {
            user = new User(login.getLogin());
            user.setCredentials(new Credentials(login.getLogin(), login.getPassword()));
            userRepository.save(user);
        } else {
            throw new UserAlreadyExistException("Login already present.");
        }
    }

    private String getToken(User user) {
        Credentials cred = user.getCredentials();
        return Jwts.builder()
                .setSubject(cred.getLogin())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConst.TOKEN_LENGTH_LIFE))
                .signWith(SignatureAlgorithm.HS512, SecurityConst.TOKEN_SECRET.getBytes())
                .compact();
    }
}
