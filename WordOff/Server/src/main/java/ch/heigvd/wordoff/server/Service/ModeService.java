package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.server.Model.Modes.Mode;
import ch.heigvd.wordoff.server.Model.User;
import org.springframework.stereotype.Service;

@Service
public class ModeService {
    private GameService gameService;

    public ModeService(GameService gameService) {
        this.gameService = gameService;
    }

    public Mode initMode(User user, ModeType modeType) {

    }
}
