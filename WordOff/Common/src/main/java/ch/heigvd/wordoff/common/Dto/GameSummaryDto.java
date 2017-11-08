package ch.heigvd.wordoff.common.Dto;

/**
 * Created by Daniel on 05.11.2017.
 */
public class GameSummaryDto implements ISummaryDto {
    private Integer id;

    private PlayerDto otherPlayer;

    private String endpoint;

    public GameSummaryDto(Integer id, PlayerDto otherPlayer) {
        this.id = id;
        this.otherPlayer = otherPlayer;
        this.endpoint = "/games/" + id;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlayerDto getOtherPlayer() {
        return otherPlayer;
    }

    public void setOtherPlayer(PlayerDto otherPlayer) {
        this.otherPlayer = otherPlayer;
    }
}
