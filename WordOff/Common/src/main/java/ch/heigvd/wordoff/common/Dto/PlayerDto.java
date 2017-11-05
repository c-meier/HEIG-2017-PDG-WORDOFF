package ch.heigvd.wordoff.common.Dto;


/**
 * Project : WordOff
 * Date : 10.10.17
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
