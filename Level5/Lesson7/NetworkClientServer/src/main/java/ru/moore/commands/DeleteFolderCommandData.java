package ru.moore.commands;

import java.io.Serializable;

public class DeleteFolderCommandData implements Serializable {

    private final String folderName;

    public DeleteFolderCommandData(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

}
