package ch.heigvd.wordoff.common.Dto;

import java.util.Objects;

/**
 * Created by Daniel on 05.11.2017.
 */
public class GameSummaryDto implements ISummaryDto {
    private Long id;

    private PlayerDto otherPlayer;

    private String endpoint;

    // Necessary for Jackson deserialization
    protected GameSummaryDto() {}

    public GameSummaryDto(Long id, PlayerDto otherPlayer) {
        this.id = id;
        this.otherPlayer = otherPlayer;
        this.endpoint = "/games/" + id;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
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
}
