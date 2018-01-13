package ch.heigvd.wordoff.server.Rest.Exception;

/**
 * Exception to throw if the client is not authorized to do an action.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String s) {
        super(s);
    }
}
