package main.java;

import main.java.commands.*;

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

    public static Command regErrorCommand(String regErrorMessage) {
        Command command = new Command();
        command.type = CommandType.REG_ERROR;
        command.data = new RegErrorCommandData(regErrorMessage);
        return command;
    }

    public static Command authCommand(String login, String password) {
        Command command = new Command();
        command.type = CommandType.AUTH;
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

    public static Command errorCommand(String errorMessage) {
        Command command = new Command();
        command.type = CommandType.ERROR;
        command.data = new ErrorCommandData(errorMessage);
        return command;
    }

    public static Command sendInfoFile(int fileSize, String fileName) {
        Command command = new Command();
        command.type = CommandType.SEND_INFO_FILE;
        command.data = new SendInfoFileCommandData(fileSize, fileName);
        return command;
    }

    public static Command fileOkCommand(String status) {
        Command command = new Command();
        command.type = CommandType.STATUS_FILE;
        command.data = new SendStatusFileCommandData(status);
        return command;
    }

/*






    public static Command messageInfoCommand(String message, String sender) {
        Command command = new Command();
        command.type = CommandType.INFO_MESSAGE;
        command.data = new MessageInfoCommandData(message, sender);
        return command;
    }

    public static Command publicMessageCommand(String username, String message) {
        Command command = new Command();
        command.type = CommandType.PUBLIC_MESSAGE;
        command.data = new PublicMessageCommandData(username, message);
        return command;
    }

    public static Command privateMessageCommand(String receiver, String message) {
        Command command = new Command();
        command.type = CommandType.PRIVATE_MESSAGE;
        command.data = new PrivateMessageCommandData(receiver, message);
        return command;
    }

    public static Command updateUsersListCommand(List<String> users) {
        Command command = new Command();
        command.type = CommandType.UPDATE_USERS_LIST;
        command.data = new UpdateUsersListCommandData(users);
        return command;
    }

    public static Command endCommand() {
        Command command = new Command();
        command.type = CommandType.END;
        return command;
    }



    public static Command saveUserNameCommand(String username) {
        Command command = new Command();
        command.type = CommandType.SAVE_USER_NAME;
        command.data = new SaveUserNameCommandData(username);
        return command;
    }*/
}
