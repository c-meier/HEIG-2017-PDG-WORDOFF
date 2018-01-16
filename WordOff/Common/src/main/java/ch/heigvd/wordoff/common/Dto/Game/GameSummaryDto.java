package ch.heigvd.wordoff.common.Dto.Game;

import ch.heigvd.wordoff.common.Dto.Endpoint.IEndpoint;
import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;
import ch.heigvd.wordoff.common.Dto.User.PlayerDto;

import java.util.Objects;

/**
 * Created by Daniel on 05.11.2017.
 */
public class GameSummaryDto implements IResource<GameDto>, IEndpoint {
    private Long id;

    private PlayerDto otherPlayer;

    private boolean ended;

    /**
     * Endpoint to GET the full game information.
     */
    private String endpoint;

    // Necessary for Jackson deserialization
    protected GameSummaryDto() {}

    public GameSummaryDto(Long id, PlayerDto otherPlayer) {
        this.setId(id);
        this.otherPlayer = otherPlayer;

    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerDto getOtherPlayer() {
        return otherPlayer;
    }

    public void setOtherPlayer(PlayerDto otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof GameSummaryDto)) {
            return false;
        }
        GameSummaryDto c = (GameSummaryDto) o;
        return Objects.equals(id, c.id) &&
                Objects.equals(endpoint, c.endpoint) &&
                Objects.equals(otherPlayer, c.otherPlayer);
    }

    private static Class<GameDto> resourceType = GameDto.class;

    @Override
    public Class<GameDto> getResourceType() {
        return resourceType;
    }
}
