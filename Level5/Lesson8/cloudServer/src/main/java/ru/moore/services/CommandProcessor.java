package ru.moore.services;

import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

import static ru.moore.services.StorageProvider.BASE_PATH;

public class CommandProcessor {

    private UsersRepository usersRepository;
    private Users users;

    private static final Logger logger = LogManager.getLogger(CommandProcessor.class.getName());

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

    public void processGetDirectory(ChannelHandlerContext ctx) {
        DirectoryCommandData tree = StorageProvider.getUserDir(users.getUsername());
        ctx.writeAndFlush(Command.setDirectoryCommand(tree));
    }

    public void processCreateFolder(Command command, ChannelHandlerContext ctx) {
        CreateFolderCommandData cmdData = (CreateFolderCommandData) command.getData();
        String nameFolder = cmdData.getFolderName();

        Path filePath = Path.of(BASE_PATH + "/" + users.getUsername() + "/" + nameFolder);
        try {
            Files.createDirectories(filePath);
        } catch (IOException e) {

        }
        ctx.writeAndFlush(command);
    }

    public void processRenameFolder(Command command, ChannelHandlerContext ctx) {
        RenameFolderCommandData cmdData = (RenameFolderCommandData) command.getData();
        String oldFolderName = cmdData.getOldFolderName();
        String newFolderName = cmdData.getNewFolderName();

        Path source = Path.of(BASE_PATH + "/" + users.getUsername() + "/" + oldFolderName);
        Path target = Path.of(BASE_PATH + "/" + users.getUsername() + "/" + newFolderName);
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

        }
        ctx.writeAndFlush(command);
    }

    public void processDeleteFolder(Command command, ChannelHandlerContext ctx) {
        DeleteFolderCommandData cmdData = (DeleteFolderCommandData) command.getData();
        String nameFolder = cmdData.getFolderName();

        StorageProvider.deleteDir(nameFolder, users.getUsername());
        ctx.writeAndFlush(command);
    }

    public void processGetFiles(Command command, ChannelHandlerContext ctx) {
        GetFilesCommandData cmdData = (GetFilesCommandData) command.getData();
        String curFolder = cmdData.getCurrentFolder();

        FilesList filesList = StorageProvider.getFilesUser(users.getUsername(), curFolder);

        ctx.writeAndFlush(Command.setFilesCommand(filesList));
    }

    public void processDownFile(Command command, ChannelHandlerContext ctx) {
        DownloadFileCommandData cmdData = (DownloadFileCommandData) command.getData();
        String curFiles = cmdData.getCurrentFile();

        Path filePath = Path.of(BASE_PATH + "/" + users.getUsername() + "/" + curFiles);
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
                logger.error(e);
            }
        }

    }

    public void processDeleteFile(Command command, ChannelHandlerContext ctx) {
        DeleteFileCommandData cmdData = (DeleteFileCommandData) command.getData();
        String curFiles = cmdData.getCurrentFile();

        Path filePath = Path.of(BASE_PATH + "/" + users.getUsername() + "/" + curFiles);
        try {
            Files.delete(filePath);
        } catch (Exception e) {
            logger.error(e);
        }
        ctx.writeAndFlush(command);
    }

    public void processRenameFile(Command command, ChannelHandlerContext ctx) {
        RenameFileCommandData cmdData = (RenameFileCommandData) command.getData();
        String currentFolder = cmdData.getCurrentFolder();
        String oldFileName = cmdData.getOldFileName();
        String newFileName = cmdData.getNewFileName();

        Path filePath = Path.of(BASE_PATH + "/" + users.getUsername() + "/" + currentFolder + "/" + oldFileName);
        filePath.normalize();
        if (Files.exists(filePath)) {
            try {
                Files.move(filePath, filePath.getParent().resolve(newFileName));
            } catch (IOException e) {
                logger.error(e);
            }
        }
        ctx.writeAndFlush(command);
    }

    public void processUploadFile(Command command, ChannelHandlerContext ctx) {
        FilePart cmdData = (FilePart) command.getData();

        Path filePath = Path.of(BASE_PATH + "/" + users.getUsername() + "/" + cmdData.getFileName());
        try {
            Files.write(filePath, cmdData.getData(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error(e);
        }

        Command filesTransferOk = Command.fileTransferOkCommand();
        ctx.writeAndFlush(filesTransferOk);
    }
}
