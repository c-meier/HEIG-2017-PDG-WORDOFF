package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.common.Dto.User.RelationStatus;
import ch.heigvd.wordoff.common.Protocol;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Repository.InvitationRepository;
import ch.heigvd.wordoff.server.Repository.NotificationRepository;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Rest.Exception.ErrorCodeException;
import ch.heigvd.wordoff.server.Rest.Exception.UnauthorizedException;
import ch.heigvd.wordoff.server.Security.SecurityConst;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    private UserRepository userRepository;
    private InvitationRepository invitationRepository;
    private NotificationRepository notificationRepository;

    public UserService(UserRepository userRepository, InvitationRepository invitationRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.notificationRepository = notificationRepository;
    }

    public User signIn(String login, char[] password) {
        User user = userRepository.findByCredentialsLogin(login);
        if(user != null) {
            if(Arrays.equals(user.getCredentials().getPassword(),password)) {
                return user;
            }
        }
        throw new UnauthorizedException("Incorrect login or password.");
    }

    public User createUser(String login, char[] password) {
        User user = userRepository.findByCredentialsLogin(login);
        if(user == null) {
            user = new User(login);
            user.setCredentials(new Credentials(login, password));
            return userRepository.save(user);
        } else {
            throw new ErrorCodeException(Protocol.USER_ALREADY_EXISTS, "The specified login already exists");
        }
    }

    public String getUserToken(User user) {
        Credentials cred = user.getCredentials();
        return SecurityConst.TOKEN_PREFIX + Jwts.builder()
                .setSubject(cred.getLogin())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConst.TOKEN_LENGTH_LIFE))
                .signWith(SignatureAlgorithm.HS512, SecurityConst.TOKEN_SECRET.getBytes())
                .compact();
    }

    /**
     * Get the invitations with the status WAITING targeting the given user.
     * @param user The given user.
     * @return The invitations with the status WAITING targeting the given user.
     */
    public List<Invitation> getUserWaitingInvitations(User user) {
        return  invitationRepository.findAllByPkTargetAndStatus(user, InvitationStatus.WAITING);
    }

    public List<Notification> getUserNotifications(User user) {
        return notificationRepository.findAllByTargetId(user.getId());
    }

    public Collection<Relation> getRelations(User owner) {
        User o = userRepository.findOne(owner.getId());
        return o.getRelations().values();
    }

    public List<User> getAdversaries(User owner) {
        return Stream.of(invitationRepository.findAllByPkTargetAndStatus(owner, InvitationStatus.ACCEPT),
                invitationRepository.findAllByPkTargetAndStatus(owner, InvitationStatus.ORIGIN))
                .flatMap(Collection::stream)
                .map(Invitation::getMode)
                .filter(m -> m.getType() == ModeType.FRIEND_DUEL || m.getType() == ModeType.RANDOM_DUEL)
                .flatMap(m -> m.getInvitations().stream()
                        .map(Invitation::getTarget)
                        .filter(u -> Objects.equals(u.getId(), owner.getId())))
                .distinct()
                .filter(user -> !Objects.equals(owner.getId(), user.getId()))
                .collect(Collectors.toList());
    }

    public Relation setUsersRelation(User user, Long targetId, RelationStatus status) {
        User target = userRepository.findOne(targetId);
        User owner = userRepository.findOne(user.getId());
        owner.setRelation(target, status);
        userRepository.saveAndFlush(owner);
        return owner.getRelation(target);
    }

    /**
     * Get the User in the database.
     * @param userId Id of the user.
     * @return The user.
     */
    public User getUser(Long userId) {
        return userRepository.findOne(userId);
    }

    /**
     * Get the User in the database.
     * @param username Username of the user.
     * @return The user.
     */
    public User getUser(String username) {
        return userRepository.findByName(username);
    }
}
