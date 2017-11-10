package ch.heigvd.wordoff.common.Dto;

import java.util.Objects;

public class UserDto extends PlayerDto {
    private int level;
    private String profilImage;

    // Necessary for Jackson deserialization
    protected UserDto() {}

    public UserDto(Long id, String name) {
        super(id, name);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(String profilImage) {
        this.profilImage = profilImage;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UserDto)) {
            return false;
        }
        UserDto c = (UserDto) o;
        return super.equals(o) &&
                Objects.equals(level, c.level) &&
                Objects.equals(profilImage, c.profilImage);
    }
}
