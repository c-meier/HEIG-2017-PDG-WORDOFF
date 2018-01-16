/*
 * File: ResourceWriteList.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.Endpoint;

/**
 * A class to transmit the endpoint for a readable and addable list of resources.
 * Allow GET and POST requests.
 * @param <RES> The class type of the content of the response.
 * @param <REQ> The class type of the content of the request. (For POST)
 */
public class ResourceWriteList<RES, REQ> extends ResourceList<RES> {

    Class<REQ> requestType;

    public ResourceWriteList() {
    }

    public ResourceWriteList(Class<RES> responseType, Class<REQ> requestType) {
        super(responseType);
        this.requestType = requestType;
    }

    public Class<REQ> getRequestType() {
        return requestType;
    }

    public void setRequestType(Class<REQ> requestType) {
        this.requestType = requestType;
    }
}
