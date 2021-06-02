package ru.moore.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilesList implements Serializable {

    private List<FileItem> list = new ArrayList<>();

    public void add(FileItem fileItem) {
        list.add(fileItem);
    }

    public List<FileItem> getList() {
        return list;
    }

}
