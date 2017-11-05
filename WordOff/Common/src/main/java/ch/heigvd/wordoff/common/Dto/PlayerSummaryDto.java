package ch.heigvd.wordoff.common.Dto;

/**
 * Created by Daniel on 05.11.2017.
 */

/**
 * Player summary with minimum of information
 * Use endpoint to get the complete player dto information.
 */
public class PlayerSummaryDto implements ISummaryDto {
    private String endpoint;

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    private Long id;

    private String name;

    public PlayerSummaryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
