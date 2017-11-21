package ch.heigvd.wordoff.common.Dto;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;

public class InvitationDto implements IResource<InvitationDto> {
    private Long invitationId;

    private InvitationStatus status;
    /**
     * Endpoint to update (PUT) the status of an invitation.
     */
    private String endpoint;

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
        if(invitationId != null) {
            setEndpoint("/me/invitations/" + invitationId);
        }
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
}
