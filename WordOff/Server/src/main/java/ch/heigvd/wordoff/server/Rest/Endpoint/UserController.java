package ch.heigvd.wordoff.server.Rest.Endpoint;

import ch.heigvd.wordoff.common.Dto.MeDto;
import ch.heigvd.wordoff.common.Dto.User.LoginDto;
import ch.heigvd.wordoff.common.Dto.User.UserDto;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Security.SecurityConst;
import ch.heigvd.wordoff.server.Service.UserService;
import ch.heigvd.wordoff.server.Util.DtoFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that allow for actions pertaining to users.
 */
@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    private UserRepository userRepository;

    private UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Logs a user if his login credentials are corrects.
     * @param login The login credentials of the user.
     * @return A HTTP response with the token as an authorization header.
     */
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

    /**
     * Create a new user with the given login credentials.
     * @param login The login credentials.
     * @return An empty HTTP response.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody LoginDto login) {
        if(!login.isWellformed()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        userService.createUser(login.getLogin(), login.getPassword());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Get information about a user from the given id.
     * @param player The current player.
     * @param userId The given user id.
     * @return The information about the user.
     */
    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public ResponseEntity<UserDto> getMode(
            @RequestAttribute("player") User player,
            @PathVariable("userId") Long userId) {
        User user = userService.getUser(userId);
        player = userService.getUser(player.getId()); // Reload the player from DB.
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserDto userDto = DtoFactory.createFrom(user, player);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
    }
}
