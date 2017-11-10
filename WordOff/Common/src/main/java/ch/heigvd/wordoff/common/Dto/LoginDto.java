package ch.heigvd.wordoff.common.Dto;

import java.util.Arrays;
import java.util.Objects;

public class LoginDto implements IDto {
    private String login;

    private char[] password;

    protected LoginDto() {}

    public LoginDto(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof LoginDto)) {
            return false;
        }
        LoginDto c = (LoginDto) o;
        return Objects.equals(login, c.login) &&
                Arrays.equals(password, c.password);
    }

    public boolean isWellformed() {
        return login != null && !login.isEmpty() && password != null && password.length > 0;
    }
}
