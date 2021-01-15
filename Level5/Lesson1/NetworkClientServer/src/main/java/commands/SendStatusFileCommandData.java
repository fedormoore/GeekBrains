package main.java.commands;

import java.io.Serializable;

public class SendStatusFileCommandData implements Serializable {

    private final String status;

    public SendStatusFileCommandData(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
