package ru.moore.commands;

import java.io.Serializable;
import java.util.ArrayList;

public class DirectoryCommandData implements Serializable {

    private final ArrayList<FileTreeItem> children = new ArrayList<>();

    public void add(FileTreeItem item) {
        children.add(item);
    }

    public ArrayList<FileTreeItem> getChildren() {
        return children;
    }
}