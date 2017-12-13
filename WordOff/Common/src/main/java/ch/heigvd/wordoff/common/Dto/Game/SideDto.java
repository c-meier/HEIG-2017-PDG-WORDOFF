package ch.heigvd.wordoff.common.Dto.Game;

import ch.heigvd.wordoff.common.Dto.Game.Racks.PlayerRackDto;
import ch.heigvd.wordoff.common.Dto.User.PlayerDto;

import java.util.Objects;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class SideDto extends OtherSideDto {

    private PlayerRackDto playerRack;

    // Necessary for Jackson deserialization
    protected SideDto() {}

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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof SideDto)) {
            return false;
        }
        SideDto c = (SideDto) o;
        return super.equals(o) &&
                Objects.equals(playerRack, c.playerRack);
    }

}
