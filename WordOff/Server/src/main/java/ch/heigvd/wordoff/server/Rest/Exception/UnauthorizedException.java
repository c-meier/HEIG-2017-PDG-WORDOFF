package ch.heigvd.wordoff.server.Rest.Exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String s) {
        super(s);
    }
}
