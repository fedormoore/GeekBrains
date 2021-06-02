package ru.moore.server.chat.handler;

import ru.moore.clientserver.Command;
import ru.moore.clientserver.CommandType;
import ru.moore.clientserver.commands.*;
import ru.moore.server.chat.MyServer;
import ru.moore.server.models.Users;
import ru.moore.server.repositories.UsersRepository;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler {

    private final MyServer myServer;
    private final Socket clientSocket;
    private UsersRepository usersRepository;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Users users;

    public ClientHandler(MyServer myServer, Socket clientSocket, UsersRepository usersRepository) {
        this.myServer = myServer;
        this.clientSocket = clientSocket;
        this.usersRepository = usersRepository;
    }

    public void handle() throws IOException {
        in = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                //authentication();
                readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    System.err.println("Failed to close connection!");
                }
            }
        }).start();
    }

    public String getUsername() {
        return users.getUsername();
    }

    private void readMessages() throws IOException {
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }

            switch (command.getType()) {
                case END:
                    return;
                case PRIVATE_MESSAGE: {
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    String recipient = data.getReceiver();
                    String privateMessage = data.getMessage();
                    myServer.sendPrivateMessage(recipient, Command.messageInfoCommand(privateMessage, users.getUsername()));
                    break;
                }
                case PUBLIC_MESSAGE: {
                    PublicMessageCommandData data = (PublicMessageCommandData) command.getData();
                    String message = data.getMessage();
                    String sender = data.getSender();
                    myServer.broadcastMessage(this, Command.messageInfoCommand(message, sender));
                    break;
                }
                case AUTH: {
                    processAuthCommand(command);
                    break;
                }
                case REG_USER: {
                    processRegCommand(command);
                    break;
                }
                case SAVE_USER_NAME: {
                    processSaveUserNameCommand(command);
                    break;
                }
                default:
                    System.err.println("Неизвестный тип команды: " + command.getType());
            }
        }
    }

    private void processSaveUserNameCommand(Command command) throws IOException {
        SaveUserNameCommandData cmdData = (SaveUserNameCommandData) command.getData();
        String username = cmdData.getUsername();
        users.setUsername(username);
        usersRepository.update(users);
        myServer.changeUserName();
    }

    private Command readCommand() throws IOException {
        try {
            return (Command) in.readObject();
        } catch (ClassNotFoundException e) {
            String errorMessage = "Неизвестная команда от клиента";
            System.err.println(errorMessage);
            e.printStackTrace();
            sendMessage(Command.errorCommand(errorMessage));
            return null;
        }
    }

    private void processAuthCommand(Command command) throws IOException {
        AuthCommandData cmdData = (AuthCommandData) command.getData();
        String login = cmdData.getLogin();
        String password = cmdData.getPassword();
        users = usersRepository.findUserByLoginAndPassword(login, password);
        if (users != null) {
            if (myServer.isNicknameAlreadyBusy(users.getUsername())) {
                sendMessage(Command.authErrorCommand("Участник уже в чате!"));
                return;
            }
            sendMessage(Command.authOkCommand(users.getUsername()));
            String message = users.getUsername() + " приветствуем в чате!";
            myServer.broadcastMessage(this, Command.messageInfoCommand(message, null));
            myServer.subscribe(this);
        } else {
            sendMessage(Command.authErrorCommand("Неверный логин или пароль!"));
        }
    }

    private void processRegCommand(Command command) throws IOException {
        RegCommandData cmdData = (RegCommandData) command.getData();
        String username = cmdData.getUsername();
        String login = cmdData.getLogin();
        String password = cmdData.getPassword();

        if (usersRepository.findUserByLogin(login)) {
            sendMessage(Command.authErrorCommand("Логин занят!"));
        } else {
            users = new Users(null, username, login, password);
            if (usersRepository.save(users) != null) {
                sendMessage(Command.authOkCommand("Успешная регистрация!"));
            }
        }
    }

    private void closeConnection() throws IOException {
        myServer.unsubscribe(this);
        clientSocket.close();
    }

    public void sendMessage(Command command) throws IOException {
        out.writeObject(command);
    }

}
