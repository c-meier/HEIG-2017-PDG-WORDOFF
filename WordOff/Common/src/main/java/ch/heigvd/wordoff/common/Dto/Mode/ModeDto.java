package ch.heigvd.wordoff.common.Dto.Mode;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceWriteList;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.GameSummaryDto;
import ch.heigvd.wordoff.common.Dto.MessageDto;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "dtype"
)
public abstract class ModeDto implements IResource<ModeDto> {
    /**
     * The type of the mode.
     */
    private ModeType type;

    /**
     * The name shown to the viewer.
     */
    private String name;

    /**
     * The date and time when the mode was started.
     */
    private LocalDateTime startDate;

    /**
     * The current game, the one that can be played.
     */
    private GameSummaryDto game;

    /**
     * Flag indicating if the mode has ended (you can never play again)
     */
    private boolean ended;

    /**
     * Endpoint to POST a game, so that a new game can be created.
     */
    private ResourceWriteList<GameDto, Void> games;

    /**
     * Endpoint to GET and POST messages (chat).
     */
    private ResourceWriteList<MessageDto, MessageDto> messages;

    /**
     * Endpoint to refresh (GET) informations.
     */
    private String endpoint;

    @Override
    public String getEndpoint() {
        return endpoint;
    }

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public GameSummaryDto getGame() {
        return game;
    }

    public void setGame(GameSummaryDto game) {
        this.game = game;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public ResourceWriteList<MessageDto, MessageDto> getMessages() {
        return messages;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        this.messages = new ResourceWriteList<>(endpoint + "/messages");
        this.games = new ResourceWriteList<>(endpoint + "/games");
    }

    public ResourceWriteList<GameDto, Void> getGames() {
        return games;
    }
}
