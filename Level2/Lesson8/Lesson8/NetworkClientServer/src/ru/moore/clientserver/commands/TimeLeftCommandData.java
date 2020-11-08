package ru.moore.clientserver.commands;

import java.io.Serializable;

public class TimeLeftCommandData implements Serializable {

    private final Integer second;

    public TimeLeftCommandData(Integer second) {
        this.second = second;
    }

    public Integer getSecond() {
        return second;
    }
}
