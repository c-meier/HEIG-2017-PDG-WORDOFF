package ch.heigvd.wordoff.common.Dto.Mode;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceWriteList;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.GameSummaryDto;
import ch.heigvd.wordoff.common.Dto.Game.PowerDto;

import java.time.LocalDate;

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
    private LocalDate startDate;

    /**
     * The current game, the one that can be played.
     */
    private GameSummaryDto game;

    /**
     * Endpoint to GET and POST messages (chat).
     */
    private ResourceWriteList<GameDto, PowerDto> messages;

    /**
     * Endpoint to refresh (GET) informations.
     */
    private String endpoint;

    @Override
    public String getEndpoint() {
        return null;
    }
}
