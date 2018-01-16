/*
 * File: Credentials.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Model;


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Class that represents the credentials of a user for the connection to the server
 */
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
