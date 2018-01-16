/*
 * File: ProtocolException.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Exception;

import javax.xml.ws.http.HTTPException;

/**
 * Abstrat exception that represents an error during the communication with the server.
 */
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
