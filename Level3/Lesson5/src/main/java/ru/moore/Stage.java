package ru.moore;

import java.util.concurrent.Semaphore;

public abstract class Stage {
    protected int length;
    protected String description;

    protected Semaphore smpTunnel = new Semaphore(2);

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);
}
