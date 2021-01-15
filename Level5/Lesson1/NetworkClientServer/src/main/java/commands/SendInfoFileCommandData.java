package main.java.commands;

import java.io.Serializable;

public class SendInfoFileCommandData implements Serializable {

    private final int fileSize;
    private final String fileName;

    public SendInfoFileCommandData(int fileSize, String fileName) {
        this.fileSize = fileSize;
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getFileName() {
        return fileName;
    }
}
