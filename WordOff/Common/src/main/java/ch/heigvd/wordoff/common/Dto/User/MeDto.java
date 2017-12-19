package ch.heigvd.wordoff.common.Dto.User;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceList;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceWriteList;
import ch.heigvd.wordoff.common.Dto.InvitationDto;
import ch.heigvd.wordoff.common.Dto.Mode.CreateModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeDto;
import ch.heigvd.wordoff.common.Dto.NotificationDto;

public class MeDto extends PlayerDto implements IResource<MeDto> {
    /**
     * Endpoint to get and put your current invitations to a mode (tournament, duel)
     */
    private ResourceWriteList<InvitationDto, InvitationDto> invitations;

    /**
     * Endpoint to get your current notifications (if its your turn to a game, ...)
     */
    private ResourceList<NotificationDto> notifications;
    /**
     * Endpoint to get your modes (duels, randoms, tournaments, competitions)
     */
    private ResourceWriteList<ModeDto, CreateModeDto> modes;

    /**
     * Endpoint to get the users with whom you have recently been in a game.
     */
    private ResourceList<UserSummaryDto> adversaries;

    /**
     * Endpoint to get your friends and blacklisted users.
     */
    private ResourceList<RelatedUserSummaryDto> relations;

    public MeDto() {
        String baseUrl = "/me";
        setEndpoint(baseUrl);
        this.notifications = new ResourceList<>(baseUrl + "/notifications");
        this.invitations = new ResourceWriteList<>(baseUrl + "/invitations");
        this.adversaries = new ResourceList<>(baseUrl + "/adversaries");
        this.relations = new ResourceList<>(baseUrl + "/relations");
        this.modes = new ResourceWriteList<>("/modes");
    }

    /**
     * Endpoint to update or modify this object.
     * TODO: check if its /me or /users/{myId} ?
     */
    private String endpoint;

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
