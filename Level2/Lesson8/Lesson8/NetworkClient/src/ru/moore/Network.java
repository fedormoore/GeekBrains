package ru.moore;

import javafx.application.Platform;
import ru.moore.clientserver.Command;
import ru.moore.clientserver.commands.*;
import ru.moore.controller.ActiveController;
import ru.moore.controller.AuthDialogController;

import java.io.*;
import java.net.Socket;

public class Network {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8190;

    private final String host;
    private final int port;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private String username;
    private boolean timerStatus = true;
    Thread thread;

    public Network() {
        this(SERVER_ADDRESS, SERVER_PORT);
    }

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            System.err.println("Соединение не было установлено!");
            e.printStackTrace();
            return false;
        }
    }

    public synchronized String sendAuthCommand(String login, String password) {
        try {
            try {
                timerStatus = false;
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Command authCommand = Command.authCommand(login, password);
            outputStream.writeObject(authCommand);

            Command command = readCommand();
            if (command == null) {
                return "Ошибочная команда с сервера";
            }
            switch (command.getType()) {
                case AUTH_OK: {
                    AuthOkCommandData data = (AuthOkCommandData) command.getData();
                    this.username = data.getUsername();
                    return null;
                }
                case AUTH_ERROR: {
                    AuthErrorCommandData data = (AuthErrorCommandData) command.getData();
                    return data.getErrorMessage();
                }
                default:
                    return "Неизвестная команда: " + command.getType();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public void waitMessages(ActiveController viewController) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Command command = readCommand();
                        if(command==null){
                            viewController.showError("Ошибка сервера", "Неизвестная команда с сервера!");
                            continue;
                        }
                        switch (command.getType()){
                            case INFO_MESSAGE:{
                                MessageInfoCommandData data = (MessageInfoCommandData) command.getData();
                                String message = data.getMessage();
                                String sender = data.getSender();
                                String formattedMsg = sender != null ? String.format("%s: %s", sender, message) : message;
                                Platform.runLater(() -> {
                                    viewController.appendMessage(formattedMsg);
                                });
                                break;
                            }
                            case UPDATE_USERS_LIST:{
                                UpdateUsersListCommandData data = (UpdateUsersListCommandData) command.getData();
                                Platform.runLater(() -> {
                                    viewController.updateUsers(data.getUsers());
                                });
                                break;
                            }
                            case ERROR:{
                                ErrorCommandData data = (ErrorCommandData) command.getData();
                                String errorMessage = data.getErrorMessage();
                                Platform.runLater(() -> {
                                    viewController.showError("Ошибка сервера", errorMessage);
                                });
                                break;
                            }
                            default:
                                Platform.runLater(() -> {
                                    viewController.showError("Неизвестная команда с сервера", command.getType().toString());
                                });
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Соединение было потеряно!");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public synchronized void timerMessages(AuthDialogController viewDialogController) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (timerStatus) {
                        Command command = readCommand();
                        switch (command.getType()) {
                            case TIME_LEFT: {
                                TimeLeftCommandData data = (TimeLeftCommandData) command.getData();
                                if (data.getSecond() == 0) {
                                    timerStatus = false;
                                }
                                Platform.runLater(() -> {
                                    viewDialogController.setTime(data.getSecond());
                                });
                            }
                            default:

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Соединение было потеряно!");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void sendMessage(String message) throws IOException {
        Command command = Command.publicMessageCommand(username, message);
        sendCommand(command);
    }

    public void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
    }

    public void sendPrivateMessage(String message, String recipient) throws IOException {
        Command command = Command.privateMessageCommand(recipient, message);
        sendCommand(command);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    private Command readCommand() throws IOException {
        try {
            return (Command) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            String errorMessage = "Неизвестная команда от клиента";
            System.err.println(errorMessage);
            e.printStackTrace();
            return null;
        }
    }
}
