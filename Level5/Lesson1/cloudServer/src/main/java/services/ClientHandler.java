package services;

import main.java.Command;
import main.java.commands.AuthCommandData;
import main.java.commands.RegCommandData;
import main.java.commands.SendInfoFileCommandData;
import models.Users;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repositories.UsersRepository;

import java.io.*;
import java.net.Socket;

public class ClientHandler {

    private static final Logger logger = LogManager.getLogger(ClientHandler.class.getName());

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
                readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    logger.error("Ошибка закрытия соединения");
                }
            }
        }).start();
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
                case AUTH: {
                    authCommand(command);
                    break;
                }
                case REG_USER: {
                    regCommand(command);
                    break;
                }
                case SEND_INFO_FILE: {
                    sendInfoFileCommand(command);
                    break;
                }
                default:
                    logger.error("Неизвестный тип команды: " + command.getType());
            }
        }
    }

    private void authCommand(Command command) throws IOException {
        AuthCommandData cmdData = (AuthCommandData) command.getData();
        String login = cmdData.getLogin();
        String password = cmdData.getPassword();
        users = usersRepository.findUserByLoginAndPassword(login, password);
        if (users != null) {
            sendMessage(Command.authOkCommand(users.getUsername()));
            myServer.subscribe(this);
        } else {
            sendMessage(Command.authErrorCommand("Неверный логин или пароль!"));
        }
    }

    private void regCommand(Command command) throws IOException {
        RegCommandData cmdData = (RegCommandData) command.getData();
        String username = cmdData.getUsername();
        String login = cmdData.getLogin();
        String password = cmdData.getPassword();

        if (usersRepository.findUserByLogin(login)) {
            sendMessage(Command.regErrorCommand("Логин занят!"));
        } else {
            users = new Users(null, username, login, password);
            if (usersRepository.save(users) != null) {
                sendMessage(Command.regOkCommand("Успешная регистрация!"));
            }
        }
    }

    private void sendInfoFileCommand(Command command) throws IOException {
        SendInfoFileCommandData cmdData = (SendInfoFileCommandData) command.getData();
        int fileSize = cmdData.getFileSize();
        String fileName = cmdData.getFileName();
        sendMessage(Command.fileOkCommand("ok"));

        BufferedInputStream bis = new BufferedInputStream (clientSocket.getInputStream());
        OutputStream os = new FileOutputStream(fileName);
        int read;
        byte[] buffer = new byte[1024];
        while (fileSize>0) {
            read = bis.read(buffer);
            os.write(buffer, 0, read);
            fileSize-=read;
        }
        os.close();
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

    public void sendMessage(Command command) throws IOException {
        out.writeObject(command);
    }

    private void closeConnection() throws IOException {
        myServer.unsubscribe(this);
        clientSocket.close();
    }

}
