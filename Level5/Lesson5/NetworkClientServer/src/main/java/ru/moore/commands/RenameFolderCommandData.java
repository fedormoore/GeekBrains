package ru.moore.commands;

import java.io.Serializable;

public class RenameFolderCommandData implements Serializable {

    private final String oldFolderName;
    private final String newFolderName;

    public RenameFolderCommandData(String oldFolderName, String newFolderName) {
        this.oldFolderName = oldFolderName;
        this.newFolderName = newFolderName;
    }

    public String getOldFolderName() {
        return oldFolderName;
    }

    public String getNewFolderName() {
        return newFolderName;
    }
}
