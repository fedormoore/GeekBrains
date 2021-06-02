package ru.moore.commands;

import java.io.Serializable;

public class RegCommandData implements Serializable {

    private final String username;
    private final String login;
    private final String password;

    public RegCommandData(String username, String login, String password) {
        this.username = username;
        this.login = login;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
