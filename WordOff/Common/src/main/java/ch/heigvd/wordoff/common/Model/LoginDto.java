package ch.heigvd.wordoff.common.Model;

public class LoginDto {
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
}
