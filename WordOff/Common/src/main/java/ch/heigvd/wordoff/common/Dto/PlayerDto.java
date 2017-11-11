package ch.heigvd.wordoff.common.Dto;

/**
 * Created by Daniel on 05.11.2017.
 */

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

/**
 * Player summary with minimum of information
 * Use endpoint to get the complete player dto information.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "dtype"
)
public class PlayerDto {
    private Long id;

    private String name;

    // Necessary for Jackson deserialization
    public PlayerDto() {}

    public PlayerDto(Long id, String name) {
        this.setId(id);
        this.setName(name);
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

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PlayerDto)) {
            return false;
        }
        PlayerDto c = (PlayerDto) o;
        return Objects.equals(id, c.id) &&
                Objects.equals(name, c.name);
    }
}
