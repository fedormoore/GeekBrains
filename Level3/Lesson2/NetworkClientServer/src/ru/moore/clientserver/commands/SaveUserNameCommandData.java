package ru.moore.clientserver.commands;

import java.io.Serializable;

public class SaveUserNameCommandData implements Serializable {

    private final String username;

    public SaveUserNameCommandData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
