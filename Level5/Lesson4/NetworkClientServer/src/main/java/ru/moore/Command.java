package ru.moore;

import ru.moore.commands.*;

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

    public static Command getDirectoryCommand(Object o) {
        Command command = new Command();
        command.type = CommandType.GET_DIR;
        command.data = o;
        return command;
    }
}
