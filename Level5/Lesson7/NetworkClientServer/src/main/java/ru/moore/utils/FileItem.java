package ru.moore.utils;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

public class FileItem implements Serializable {

    private String name;
    private long size;
    private boolean exist;

    public static FileItem fromPath(Path path) {
        FileItem fileItem = new FileItem();
        String pathname = String.valueOf(path);
        File file = new File(pathname);
        if (file.exists()) {
            fileItem.name = file.getName();
            fileItem.size = file.length();
        }
        return fileItem;
    }

    public String getName() {
        return name;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public boolean isExist() {
        return exist;
    }

    public long getSize() {
        return size;
    }
}
