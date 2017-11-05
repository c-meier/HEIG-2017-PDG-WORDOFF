package ch.heigvd.wordoff.common.Model;

public class LoginDto {
    private String login;

    private String password;

    protected LoginDto() {}

    public LoginDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
