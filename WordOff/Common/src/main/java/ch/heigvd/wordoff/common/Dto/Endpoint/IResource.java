/*
 * File: IResource.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.Endpoint;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * An interface which tells a class is a single resource in the REST API.
 * @param <RES> The class type of the response.
 */
public interface IResource<RES> extends IEndpoint {
    @JsonIgnore
    Class<RES> getResourceType();
}
