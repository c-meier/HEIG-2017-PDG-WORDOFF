package ch.heigvd.wordoff.common.Dto;

/**
 * Created by Daniel on 05.11.2017.
 */
public class GameSummaryDto implements ISummaryDto {
    private String endpoint;

    @Override
    public String getEndpoint() {
        return endpoint;
    }
}
