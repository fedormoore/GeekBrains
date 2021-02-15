package ru.moore.commands;

import java.io.Serializable;

public class DownloadFileCommandData implements Serializable {

    private final String currentFile;

    public DownloadFileCommandData(String currentFile) {
        this.currentFile = currentFile;
    }

    public String getCurrentFile() {
        return currentFile;
    }
}
