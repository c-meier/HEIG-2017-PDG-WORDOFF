package ch.heigvd.wordoff.common.Dto.User;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;

public class RelationDto implements IResource<RelationDto> {
    private RelationStatus status;

    public RelationStatus getStatus() {
        return status;
    }

    public void setStatus(RelationStatus status) {
        this.status = status;
    }

    /**
     * Endpoint to update (PUT) the status of a relation.
     */
    private String endpoint;

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    private static Class<RelationDto> resourceType = RelationDto.class;

    @Override
    public Class<RelationDto> getResourceType() {
        return resourceType;
    }
}
