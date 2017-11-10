package ch.heigvd.wordoff.client.Exception;

import javax.xml.ws.http.HTTPException;

public abstract class ProtocolException extends HTTPException {

    private final String errorMessage;

    public ProtocolException(int statusCode, String errorMessage) {
        super(statusCode);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
