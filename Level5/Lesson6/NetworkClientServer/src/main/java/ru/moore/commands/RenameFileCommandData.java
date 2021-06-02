package ru.moore.commands;

import java.io.Serializable;

public class RenameFileCommandData implements Serializable {

    private final String currentFolder;
    private final String oldFileName;
    private final String newFileName;

    public RenameFileCommandData(String currentFolder, String oldFileName, String newFileName) {
        this.currentFolder = currentFolder;
        this.oldFileName = oldFileName;
        this.newFileName = newFileName;
    }

    public String getCurrentFolder() {
        return currentFolder;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }
}
