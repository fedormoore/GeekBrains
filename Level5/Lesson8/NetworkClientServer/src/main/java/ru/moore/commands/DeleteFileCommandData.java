package ru.moore.commands;

import java.io.Serializable;

public class DeleteFileCommandData implements Serializable {

    private final String currentFile;

    public DeleteFileCommandData(String currentFile) {
        this.currentFile = currentFile;
    }

    public String getCurrentFile() {
        return currentFile;
    }
}
