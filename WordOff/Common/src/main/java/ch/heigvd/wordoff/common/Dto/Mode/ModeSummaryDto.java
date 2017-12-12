package ch.heigvd.wordoff.common.Dto.Mode;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;

public class ModeSummaryDto implements IResource<ModeSummaryDto> {

    private String name;

    /**
     * Endpoint to refresh (GET) informations.
     */
    private String endpoint;
    
    @Override
    public String getEndpoint() {
        return null;
    }
}
