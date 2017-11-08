package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.Dto.LoginDto;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.Credentials;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import ch.heigvd.wordoff.server.Rest.Exception.UnauthorizedException;
import ch.heigvd.wordoff.server.Security.SecurityConst;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signIn(LoginDto login) {
        User user = userRepository.findByCredentialsLogin(login.getLogin());
        if(user != null) {
            if(Arrays.equals(user.getCredentials().getPassword(),login.getPassword())) {
                String token = getToken(user);
                return SecurityConst.TOKEN_PREFIX + token;
            }
        }
        throw new UnauthorizedException("Incorrect login or password.");
    }

    public void signUp(LoginDto login) {
        User user = userRepository.findByCredentialsLogin(login.getLogin());
        if(user == null) {
            user = new User(login.getLogin());
            user.setCredentials(new Credentials(login.getLogin(), login.getPassword()));
            userRepository.save(user);
        } else {
            throw new ErrorCodeException(Protocol.USER_ALREADY_EXISTS, "The specified login already exists");
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
