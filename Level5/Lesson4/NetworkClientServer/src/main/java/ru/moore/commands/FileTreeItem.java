package ru.moore.commands;

import java.io.Serializable;

public class FileTreeItem implements Serializable {

    private String name;
    private String path;

    @Override
    public String toString() {
        return name;
    }

    public FileTreeItem(String name, String path) {
        this.name = name;
        this.path = path.replaceAll("\\\\", "/");
    }

    public String getPath() {
        return path;
    }
}
