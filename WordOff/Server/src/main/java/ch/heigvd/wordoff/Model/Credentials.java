package ch.heigvd.wordoff.Model;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credentials {
    @Column(unique = true)
    private String login;

    private char[] password;

    protected Credentials() {}

    public Credentials(String login, char[] password) {
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
