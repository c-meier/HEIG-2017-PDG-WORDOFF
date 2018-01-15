package ch.heigvd.wordoff.common.Dto.Endpoint;

/**
 * A class to transmit the endpoint for a readable list of resources.
 * Allow a GET request.
 * @param <RES> The class type of the content of the response.
 */
public class ResourceList<RES> implements IEndpoint{
    String endpoint;

    Class<RES> responseType;

    public ResourceList() {
    }

    public ResourceList(Class<RES> responseType) {
        this.responseType = responseType;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Class<RES> getResponseType() {
        return responseType;
    }

    public void setResponseType(Class<RES> responseType) {
        this.responseType = responseType;
    }
}
