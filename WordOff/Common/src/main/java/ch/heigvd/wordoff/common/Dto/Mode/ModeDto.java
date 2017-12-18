package ch.heigvd.wordoff.common.Dto.Mode;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceWriteList;
import ch.heigvd.wordoff.common.Dto.Game.GameDto;
import ch.heigvd.wordoff.common.Dto.Game.PowerDto;

public abstract class ModeDto implements IResource<ModeDto> {

    private String name;

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
