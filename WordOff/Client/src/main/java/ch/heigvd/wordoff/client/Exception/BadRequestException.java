package ch.heigvd.wordoff.client.Exception;

public class BadRequestException extends ProtocolException {

    private static final int statusCode = 400;
    private static final String errorMessage = "The request was malformed.";

    public BadRequestException() {
        super(statusCode, errorMessage);
    }
}