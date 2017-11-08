package ch.heigvd.wordoff.common.Dto;

import ch.heigvd.wordoff.common.Dto.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;

import java.util.List;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class SideDto extends OtherSideDto {

    private PlayerRackDto playerRack;

    public SideDto(Long id, PlayerDto player, ChallengeDto challenge, PlayerRackDto playerRack,
                   int score) {
        super(id, player, challenge, score);
        this.playerRack = playerRack;
    }

    public PlayerRackDto getPlayerRack() {
        return playerRack;
    }

    public void setPlayerRack(PlayerRackDto playerRack) {
        this.playerRack = playerRack;
    }

}
