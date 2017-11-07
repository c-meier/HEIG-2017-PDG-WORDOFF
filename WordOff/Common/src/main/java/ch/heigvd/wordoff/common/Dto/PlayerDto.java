package ch.heigvd.wordoff.common.Dto;

/**
 * Created by Daniel on 05.11.2017.
 */

/**
 * Player summary with minimum of information
 * Use endpoint to get the complete player dto information.
 */
public class PlayerDto {
    private Long id;

    private String name;

    public PlayerDto(Long id, String name) {
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
