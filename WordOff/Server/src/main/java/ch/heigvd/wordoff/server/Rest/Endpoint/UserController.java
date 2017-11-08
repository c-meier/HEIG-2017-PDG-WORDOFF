package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.LoginDto;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Security.SecurityConst;
import ch.heigvd.wordoff.server.Service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    private UserRepository userRepository;

    private UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<String> test() {
        return Arrays.asList("one", "two");
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public HttpEntity signIn(@RequestBody LoginDto login) {
        if(!login.isWellformed()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String authHeader = userService.signIn(login);
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConst.AUTH_HEADER, authHeader);
        return new ResponseEntity(
                headers,HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public HttpEntity signUp(@RequestBody LoginDto login) {
        if(!login.isWellformed()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        userService.signUp(login);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
