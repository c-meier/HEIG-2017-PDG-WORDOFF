/*
 * File: BadRequestException.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.client.Exception;

/**
 * Error thrown when a request is malformed.
 */
public class BadRequestException extends ProtocolException {

    private static final int statusCode = 400;
    private static final String errorMessage = "The request was malformed.";

    public BadRequestException() {
        super(statusCode, errorMessage);
    }
}
