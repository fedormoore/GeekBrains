package ru.moore.network;

import ru.moore.Command;

@FunctionalInterface
public interface CommandCallback {
    void call(Command command);
}
