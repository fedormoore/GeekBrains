package ru.moore.commands;

import java.io.Serializable;

public class CreateFolderCommandData implements Serializable {

    private final String folderName;

    public CreateFolderCommandData(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

}
