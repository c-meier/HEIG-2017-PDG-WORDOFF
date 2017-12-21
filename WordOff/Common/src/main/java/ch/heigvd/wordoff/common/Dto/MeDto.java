package ch.heigvd.wordoff.common.Dto;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceList;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceWriteList;
import ch.heigvd.wordoff.common.Dto.Mode.CreateModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeDto;
import ch.heigvd.wordoff.common.Dto.User.RelatedUserSummaryDto;
import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;

public class MeDto implements IResource<MeDto> {
    /**
     * The summary of the current user.
     */
    private UserSummaryDto self;

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
    /**
     * Endpoint to refresh this object.
     */
    private String endpoint;

    public MeDto() {
        String baseUrl = "/me";
        setEndpoint(baseUrl);
        this.notifications = new ResourceList<>(baseUrl + "/notifications");
        this.invitations = new ResourceWriteList<>(baseUrl + "/invitations");
        this.adversaries = new ResourceList<>(baseUrl + "/adversaries");
        this.relations = new ResourceList<>(baseUrl + "/relations");
        this.modes = new ResourceWriteList<>("/modes");
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public UserSummaryDto getSelf() {
        return self;
    }

    public void setSelf(UserSummaryDto self) {
        this.self = self;
    }

    public ResourceWriteList<InvitationDto, InvitationDto> getInvitations() {
        return invitations;
    }

    public void setInvitations(ResourceWriteList<InvitationDto, InvitationDto> invitations) {
        this.invitations = invitations;
    }

    public ResourceList<NotificationDto> getNotifications() {
        return notifications;
    }

    public void setNotifications(ResourceList<NotificationDto> notifications) {
        this.notifications = notifications;
    }

    public ResourceWriteList<ModeDto, CreateModeDto> getModes() {
        return modes;
    }

    public void setModes(ResourceWriteList<ModeDto, CreateModeDto> modes) {
        this.modes = modes;
    }

    public ResourceList<UserSummaryDto> getAdversaries() {
        return adversaries;
    }

    public void setAdversaries(ResourceList<UserSummaryDto> adversaries) {
        this.adversaries = adversaries;
    }

    public ResourceList<RelatedUserSummaryDto> getRelations() {
        return relations;
    }

    public void setRelations(ResourceList<RelatedUserSummaryDto> relations) {
        this.relations = relations;
    }
}
