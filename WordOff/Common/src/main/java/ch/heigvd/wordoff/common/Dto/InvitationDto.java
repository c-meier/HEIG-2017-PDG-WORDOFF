/*
 * File: InvitationDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;

public class InvitationDto implements IResource<InvitationDto> {
    private String name;
    private InvitationStatus status;

    /**
     * Endpoint to update (PUT) the status of an invitation.
     */
    private String endpoint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    private static Class<InvitationDto> resourceType = InvitationDto.class;

    @Override
    public Class<InvitationDto> getResourceType() {
        return resourceType;
    }
}
