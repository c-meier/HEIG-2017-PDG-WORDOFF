package ch.heigvd.wordoff.common.Dto;

public class UserDto extends PlayerDto {
    private int level;
    private String profilImage;

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
}
