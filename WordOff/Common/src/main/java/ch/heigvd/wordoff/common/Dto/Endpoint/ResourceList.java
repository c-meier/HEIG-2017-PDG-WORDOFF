package ch.heigvd.wordoff.common.Dto.Endpoint;

/**
 * A class to transmit the endpoint for a readable list of resources.
 * Allow a GET request.
 * @param <RES> The class type of the content of the response.
 */
public class ResourceList<RES> implements IEndpoint{
    String endpoint;

    public ResourceList() {}

    public ResourceList(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
