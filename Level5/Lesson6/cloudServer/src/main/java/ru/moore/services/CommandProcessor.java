package ru.moore.services;

import io.netty.channel.ChannelHandlerContext;
import ru.moore.Command;
import ru.moore.commands.*;
import ru.moore.models.Users;
import ru.moore.repositories.UsersRepository;
import ru.moore.utils.FilePart;
import ru.moore.utils.FilesList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class CommandProcessor {

    private UsersRepository usersRepository;
    private Users users;

    public CommandProcessor(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void processRegCommand(Command command, ChannelHandlerContext ctx) throws IOException {
        RegCommandData cmdData = (RegCommandData) command.getData();
        String username = cmdData.getUsername();
        String login = cmdData.getLogin();
        String password = cmdData.getPassword();

        if (usersRepository.findUserByLogin(login)) {
            ctx.writeAndFlush(Command.regErrorCommand("Логин занят!"));
        } else {
            users = new Users(null, username, login, password);
            if (usersRepository.save(users) != null) {
                StorageProvider.createRootPath(username);
                ctx.writeAndFlush(Command.regOkCommand("Успешная регистрация!"));
            }
        }
    }

    public void processAuthCommand(Command command, ChannelHandlerContext ctx) {
        AuthCommandData cmdData = (AuthCommandData) command.getData();
        String login = cmdData.getLogin();
        String password = cmdData.getPassword();
        users = usersRepository.findUserByLoginAndPassword(login, password);
        if (users != null) {
            ctx.writeAndFlush(Command.authOkCommand(users.getUsername()));
        } else {
            ctx.writeAndFlush(Command.authErrorCommand("Неверный логин или пароль!"));
        }
    }

    public void processGetDirectory(ChannelHandlerContext ctx) throws Exception {
        DirectoryCommandData tree = StorageProvider.getUserDir(users.getUsername());
        ctx.writeAndFlush(Command.setDirectoryCommand(tree));
    }

    public void processCreateFolder(Command command, ChannelHandlerContext ctx) throws IOException {
        CreateFolderCommandData cmdData = (CreateFolderCommandData) command.getData();
        String nameFolder = cmdData.getFolderName();

        Path filePath = StorageProvider.getDirectoryPath(users.getUsername(), nameFolder);
        try {
            Files.createDirectories(filePath);
        } catch (IOException e) {

        }
        ctx.writeAndFlush(command);
    }

    public void processRenameFolder(Command command, ChannelHandlerContext ctx) throws IOException {
        RenameFolderCommandData cmdData = (RenameFolderCommandData) command.getData();
        String oldFolderName = cmdData.getOldFolderName();
        String newFolderName = cmdData.getNewFolderName();

        Path source = StorageProvider.getDirectoryPath(users.getUsername(), oldFolderName);
        Path target = StorageProvider.getDirectoryPath(users.getUsername(), newFolderName);
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

        }
        ctx.writeAndFlush(command);
    }

    public void processDeleteFolder(Command command, ChannelHandlerContext ctx) throws Exception {
        DeleteFolderCommandData cmdData = (DeleteFolderCommandData) command.getData();
        String nameFolder = cmdData.getFolderName();

        StorageProvider.deleteDir(nameFolder, users.getUsername());
        ctx.writeAndFlush(command);
    }

    public void processGetFiles(Command command, ChannelHandlerContext ctx) throws Exception {
        GetFilesCommandData cmdData = (GetFilesCommandData) command.getData();
        String curFolder = cmdData.getCurrentFolder();

        FilesList filesList = StorageProvider.getFilesUser(users.getUsername(), curFolder);

        ctx.writeAndFlush(Command.setFilesCommand(filesList));
    }

    public void processDownFile(Command command, ChannelHandlerContext ctx) throws IOException {
        DownloadFileCommandData cmdData = (DownloadFileCommandData) command.getData();
        String curFiles = cmdData.getCurrentFile();

        Path filePath = Path.of(StorageProvider.getDirectoryPath("", users.getUsername()) + "/" + curFiles);
        String fileName = filePath.getFileName().toString();

        if (Files.exists(filePath)) {
            byte[] buffer = new byte[FilePart.partSize];

            int read;

            try (InputStream inputStream = new FileInputStream(filePath.toAbsolutePath().toString())) {
                float size = inputStream.available();
                int iterator = 1;
                Command response;
                while ((read = inputStream.read(buffer)) != -1) {
                    float progress = ((float) (buffer.length * iterator)) / size;
                    response = Command.filePartTransferCommand(fileName, buffer, read, false, progress, iterator);
                    ctx.writeAndFlush(response);
                    iterator++;
                }
                response = Command.filePartTransferCommand(fileName, buffer, read, true, 0, iterator);
                ctx.writeAndFlush(response);
            } catch (IOException e) {

            }
        }

    }

    public void processDeleteFile(Command command, ChannelHandlerContext ctx) throws IOException {
        DeleteFileCommandData cmdData = (DeleteFileCommandData) command.getData();
        String curFiles = cmdData.getCurrentFile();

        Path filePath = Path.of(StorageProvider.getDirectoryPath("", users.getUsername()) + "/" + curFiles);
        try {
            Files.delete(filePath);
        } catch (Exception e) {

        }
        ctx.writeAndFlush(command);
    }

    public void processRenameFile(Command command, ChannelHandlerContext ctx) throws IOException {
        RenameFileCommandData cmdData = (RenameFileCommandData) command.getData();
        String currentFolder = cmdData.getCurrentFolder();
        String oldFileName = cmdData.getOldFileName();
        String newFileName = cmdData.getNewFileName();

        Path filePath = Paths.get(StorageProvider.getDirectoryPath(users.getUsername(), currentFolder) + "/" + oldFileName);
        filePath.normalize();
        if (Files.exists(filePath)) {
            try {
                Files.move(filePath, filePath.getParent().resolve(newFileName));
            } catch (IOException e) {

            }
        }
        ctx.writeAndFlush(command);
    }

    public void processUploadFile(Command command, ChannelHandlerContext ctx) throws IOException {
        FilePart cmdData = (FilePart) command.getData();

        Path filePath = Path.of(StorageProvider.getDirectoryPath(users.getUsername(), "")+"/"+cmdData.getFileName());
        try {
            Files.write(filePath, cmdData.getData(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {

        }

        Command filesTransferOk = Command.fileTransferOkCommand();
        ctx.writeAndFlush(filesTransferOk);
    }
}
