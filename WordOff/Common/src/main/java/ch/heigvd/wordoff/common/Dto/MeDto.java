package ch.heigvd.wordoff.common.Dto;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceList;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceWriteList;
import ch.heigvd.wordoff.common.Dto.Mode.CreateModeDto;
import ch.heigvd.wordoff.common.Dto.Mode.ModeSummaryDto;
import ch.heigvd.wordoff.common.Dto.User.CreateRelationDto;
import ch.heigvd.wordoff.common.Dto.User.RelatedUserSummaryDto;
import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;

public class MeDto implements IResource<MeDto> {
    /**
     * The summary of the current user.
     */
    private UserSummaryDto self;

    private static Class<MeDto> resourceType = MeDto.class;
    /**
     * Endpoint to get and put your current invitations to a mode (tournament, duel)
     */
    private ResourceWriteList<InvitationDto, InvitationDto> invitations = new ResourceWriteList<>(InvitationDto.class, InvitationDto.class);
    /**
     * Endpoint to get your current notifications (if its your turn to a game, ...)
     */
    private ResourceList<NotificationDto> notifications = new ResourceList<>(NotificationDto.class);
    /**
     * Endpoint to get your modes (duels, randoms, tournaments, competitions)
     */
    private ResourceWriteList<ModeSummaryDto, CreateModeDto> modes = new ResourceWriteList<>(ModeSummaryDto.class, CreateModeDto.class);
    /**
     * Endpoint to get the users with whom you have recently been in a game.
     */
    private ResourceList<UserSummaryDto> adversaries = new ResourceList<>(UserSummaryDto.class);
    /**
     * Endpoint to refresh this object.
     */
    private String endpoint;

    /**
     * Endpoint to get your friends and blacklisted users and to add a new relation.
     */
    private ResourceWriteList<RelatedUserSummaryDto, CreateRelationDto> relations = new ResourceWriteList<>(RelatedUserSummaryDto.class, CreateRelationDto.class);

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

    public MeDto() {
        String baseUrl = "/me";
        setEndpoint(baseUrl);
        this.notifications.setEndpoint(baseUrl + "/notifications");
        this.invitations.setEndpoint(baseUrl + "/invitations");
        this.adversaries.setEndpoint(baseUrl + "/adversaries");
        this.relations.setEndpoint(baseUrl + "/relations");
        this.modes.setEndpoint("/modes");
    }

    public ResourceWriteList<ModeSummaryDto, CreateModeDto> getModes() {
        return modes;
    }

    public ResourceList<UserSummaryDto> getAdversaries() {
        return adversaries;
    }

    public void setAdversaries(ResourceList<UserSummaryDto> adversaries) {
        this.adversaries = adversaries;
    }

    public ResourceWriteList<RelatedUserSummaryDto, CreateRelationDto> getRelations() {
        return relations;
    }

    public void setRelations(ResourceWriteList<RelatedUserSummaryDto, CreateRelationDto> relations) {
        this.relations = relations;
    }

    public void setModes(ResourceWriteList<ModeSummaryDto, CreateModeDto> modes) {
        this.modes = modes;
    }

    @Override
    public Class<MeDto> getResourceType() {
        return resourceType;
    }
}
