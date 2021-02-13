package ru.moore.services;

import io.netty.channel.ChannelHandlerContext;
import ru.moore.Command;
import ru.moore.commands.AuthCommandData;
import ru.moore.commands.DirectoryCommandData;
import ru.moore.commands.RegCommandData;
import ru.moore.models.Users;
import ru.moore.repositories.UsersRepository;

import java.io.IOException;

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
        ctx.writeAndFlush(Command.getDirectoryCommand(tree));
    }
}
