package ch.heigvd.wordoff.client.Exception;

public class UnprocessableEntityException extends ProtocolException {

    private static final int statusCode = 422;
    private static final String errorMessage = "The request was malformed.";

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
