/*
 * File: IEndpoint.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.Endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An interface which provide an endpoint to the REST API.
 */
public interface IEndpoint {
    @JsonProperty(value = "_link")
    String getEndpoint();
}
