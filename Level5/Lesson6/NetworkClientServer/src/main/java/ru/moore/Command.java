package ru.moore;

import ru.moore.commands.*;
import ru.moore.utils.FilePart;

import java.io.Serializable;

public class Command implements Serializable {

    private CommandType type;
    private Object data;

    public CommandType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public static Command regCommand(String username, String login, String password) {
        Command command = new Command();
        command.type = CommandType.REG_USER;
        command.data = new RegCommandData(username, login, password);
        return command;
    }

    public static Command regOkCommand(String username) {
        Command command = new Command();
        command.type = CommandType.REG_OK;
        command.data = new RegOkCommandData(username);
        return command;
    }

    public static Command regErrorCommand(String authErrorMessage) {
        Command command = new Command();
        command.type = CommandType.REG_ERROR;
        command.data = new RegErrorCommandData(authErrorMessage);
        return command;
    }

    public static Command authCommand(String login, String password) {
        Command command = new Command();
        command.type = CommandType.AUTH_USER;
        command.data = new AuthCommandData(login, password);
        return command;
    }

    public static Command authOkCommand(String username) {
        Command command = new Command();
        command.type = CommandType.AUTH_OK;
        command.data = new AuthOkCommandData(username);
        return command;
    }

    public static Command authErrorCommand(String authErrorMessage) {
        Command command = new Command();
        command.type = CommandType.AUTH_ERROR;
        command.data = new AuthErrorCommandData(authErrorMessage);
        return command;
    }

    public static Command getDirectoryCommand() {
        Command command = new Command();
        command.type = CommandType.GET_DIR;
        return command;
    }

    public static Command setDirectoryCommand(Object o) {
        Command command = new Command();
        command.type = CommandType.SET_DIR;
        command.data = o;
        return command;
    }

    public static Command createFolderCommand(String folderName) {
        Command command = new Command();
        command.type = CommandType.CREATE_FOLDER;
        command.data = new CreateFolderCommandData(folderName);
        return command;
    }

    public static Command renameFolderCommand(String oldFolderName, String newFolderName) {
        Command command = new Command();
        command.type = CommandType.RENAME_FOLDER;
        command.data = new RenameFolderCommandData(oldFolderName, newFolderName);
        return command;
    }

    public static Command deleteFolderCommand(String folderName) {
        Command command = new Command();
        command.type = CommandType.DELETE_FOLDER;
        command.data = new DeleteFolderCommandData(folderName);
        return command;
    }

    public static Command getFilesCommand(String currentFolder) {
        Command command = new Command();
        command.type = CommandType.GET_FILES;
        command.data = new GetFilesCommandData(currentFolder);;
        return command;
    }

    public static Command setFilesCommand(Object o) {
        Command command = new Command();
        command.type = CommandType.SET_FILES;
        command.data = o;
        return command;
    }

    public static Command getDownloadFileCommand(String currentFile) {
        Command command = new Command();
        command.type = CommandType.FILE_DOWN;
        command.data = new DownloadFileCommandData(currentFile);
        return command;
    }

    public static Command filePartTransferCommand(String fileName, byte[] fileData, int dataLength, boolean isEnd, double progress, int partNumber) {
        Command command = new Command();
        command.type = CommandType.FILE_UPLOADER;
        command.data = new FilePart(fileName, fileData, dataLength, isEnd, progress, partNumber);
        return command;
    }

    public static Command deleteFileCommand(String currentFile) {
        Command command = new Command();
        command.type = CommandType.DELETE_FILE;
        command.data = new DeleteFileCommandData(currentFile);
        return command;
    }

    public static Command renameFilesCommand(String currentFolder, String oldFileName, String newFileName) {
        Command command = new Command();
        command.type = CommandType.RENAME_FILE;
        command.data = new RenameFileCommandData(currentFolder, oldFileName, newFileName);
        return command;
    }

    public static Command fileTransferOkCommand() {
        Command command = new Command();
        command.type = CommandType.FILE_UPLOADER_RESULT;
        return command;
    }
}
