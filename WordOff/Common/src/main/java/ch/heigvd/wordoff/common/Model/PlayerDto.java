package ch.heigvd.wordoff.common.Model;

import javax.persistence.*;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class PlayerDto {
    private Long id;

    private String name;

    protected PlayerDto() {}

    public PlayerDto(String name) {
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
