package ru.moore.services;

import io.netty.channel.ChannelHandlerContext;
import ru.moore.Command;
import ru.moore.commands.*;
import ru.moore.models.Users;
import ru.moore.repositories.UsersRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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

    public void processGetDirectory(ChannelHandlerContext ctx) throws Exception{
        DirectoryCommandData tree = StorageProvider.getUserDir(users.getUsername());
        ctx.writeAndFlush(Command.setDirectoryCommand(tree));
    }

    public void processCreateFolder(Command command, ChannelHandlerContext ctx) throws IOException {
        CreateFolderCommandData cmdData = (CreateFolderCommandData) command.getData();
        String nameFolder = cmdData.getFolderName();

        Path filePath = StorageProvider.getFilePath(users.getUsername(), nameFolder);
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

        Path source = StorageProvider.getFilePath(users.getUsername(), oldFolderName);
        Path target = StorageProvider.getFilePath(users.getUsername(), newFolderName);
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
}
