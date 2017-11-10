package ch.heigvd.wordoff.client.Exception;

public class UnauthorizedException extends ProtocolException {

    private static final int statusCode = 401;
    private static final String errorMessage = "No authorization has been given.";

    public UnauthorizedException() {
        super(statusCode, errorMessage);
    }
}
