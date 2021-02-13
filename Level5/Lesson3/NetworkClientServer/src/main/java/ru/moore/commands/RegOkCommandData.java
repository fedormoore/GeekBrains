package ru.moore.commands;

import java.io.Serializable;

public class RegOkCommandData implements Serializable {

    private final String status;

    public RegOkCommandData(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
