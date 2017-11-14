package ch.heigvd.wordoff.common.Dto;

import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceWriteList;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.PowerDto;

public class ModeDto {

    /**
     * Endpoint to GET and POST messages (chat).
     */
    private ResourceWriteList<GameDto, PowerDto> messages;
}
