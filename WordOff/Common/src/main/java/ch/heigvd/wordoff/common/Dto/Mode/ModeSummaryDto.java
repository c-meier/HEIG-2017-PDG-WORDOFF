package ch.heigvd.wordoff.common.Dto.Mode;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;

public class ModeSummaryDto implements IResource<ModeDto> {
    /**
     * The type of the mode.
     */
    private ModeType type;

    /**
     * The name shown to the viewer.
     */
    private String name;

    /**
     * Flag indicating if the mode has ended (you can never play again)
     */
    private boolean ended;

    /**
     * Flag indicating if the player can effectuate an action in the mode.
     * For example: respond to a challenge, or play for the current day in a tournament.
     */
    private boolean active;

    /**
     * Endpoint to refresh (GET) informations.
     */
    private String endpoint;

    public ModeType getType() {
        return type;
    }

    public void setType(ModeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    private static Class<ModeDto> resourceType = ModeDto.class;

    @Override
    public Class<ModeDto> getResourceType() {
        return resourceType;
    }
}
