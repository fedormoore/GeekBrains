package ru.moore.commands;

import java.io.Serializable;

public class GetFilesCommandData implements Serializable {

    private final String currentFolder;

    public GetFilesCommandData(String currentFolder) {
        this.currentFolder = currentFolder;
    }

    public String getCurrentFolder() {
        return currentFolder;
    }
}
