package ch.heigvd.wordoff.client.Exception;

/**
 * Error thrown when the processing of the content of the request produced an exeption.
 */
public class UnprocessableEntityException extends ProtocolException {

    private static final int statusCode = 422;
    private static final String errorMessage = "The content of the request produced, when processed, an exception.";

    private final int errorCode;
    private final String msg;

    public UnprocessableEntityException(int errorCode, String msg) {
        super(statusCode, errorMessage);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getMsg() {
        return this.msg;
    }
}
