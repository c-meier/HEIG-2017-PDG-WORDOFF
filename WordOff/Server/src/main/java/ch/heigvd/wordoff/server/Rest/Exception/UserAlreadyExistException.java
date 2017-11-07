package ch.heigvd.wordoff.server.Rest.Exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String s) {
        super(s);
    }
}
