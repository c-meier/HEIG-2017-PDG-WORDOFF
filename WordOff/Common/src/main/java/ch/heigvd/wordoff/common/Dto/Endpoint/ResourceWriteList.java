package ch.heigvd.wordoff.common.Dto.Endpoint;

/**
 * A class to transmit the endpoint for a readable and addable list of resources.
 * Allow GET and POST requests.
 * @param <RES> The class type of the content of the response.
 * @param <REQ> The class type of the content of the request. (For POST)
 */
public class ResourceWriteList<RES, REQ> extends ResourceList<RES> {
    public ResourceWriteList() {
        super();
    }

    public ResourceWriteList(String endpoint) {
        super(endpoint);
    }
}
