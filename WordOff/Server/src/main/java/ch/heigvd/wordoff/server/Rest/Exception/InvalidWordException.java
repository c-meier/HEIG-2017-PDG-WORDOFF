package ch.heigvd.wordoff.server.Rest.Exception;

public class InvalidWordException extends RuntimeException {
    public InvalidWordException(String s) {
        super(s);
    }
}
