package ch.heigvd.wordoff.common.Dto.Mode;

import java.util.List;

/**
 * Send information about a new mode to the server.
 */
public class CreateModeDto {
    /**
     * The name of the mode. Required if tournament mode.
     */
    private String name;

    /**
     * The type of the mode.
     */
    private ModeType type;

    /**
     * The list of users participating to the mode.
     * Each user is represented by its username.
     * The current user takes part to the mode but MUST not be in this list.
     */
    private List<String> participants;

    public CreateModeDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModeType getType() {
        return type;
    }

    public void setType(ModeType type) {
        this.type = type;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public boolean addParticpant(String s) {
        return getParticipants().add(s);
    }
}
