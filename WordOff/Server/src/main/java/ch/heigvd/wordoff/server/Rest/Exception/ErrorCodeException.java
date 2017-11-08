package ch.heigvd.wordoff.server.Rest.Exception;

public class ErrorCodeException extends RuntimeException {
    private int code;

    public ErrorCodeException(int code, String s) {
        super(s);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
