package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.User.LoginDto;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Security.SecurityConst;
import ch.heigvd.wordoff.server.Service.UserService;
import ch.heigvd.wordoff.server.Util.DtoFactory;
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
    public ResponseEntity<MeDto> signIn(@RequestBody LoginDto login) {
        if(!login.isWellformed()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User loggedUser = userService.signIn(login.getLogin(), login.getPassword());
        String authHeader = userService.getUserToken(loggedUser);
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConst.AUTH_HEADER, authHeader);
        return new ResponseEntity<>(DtoFactory.createMeFrom(loggedUser), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody LoginDto login) {
        if(!login.isWellformed()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        userService.createUser(login.getLogin(), login.getPassword());
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
