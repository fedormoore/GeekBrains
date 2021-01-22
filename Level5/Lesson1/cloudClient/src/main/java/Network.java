package main.java;

import main.java.commands.AuthErrorCommandData;
import main.java.commands.AuthOkCommandData;

import java.io.*;
import java.net.Socket;

public class Network {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8190;

    private Socket socket;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private final String host;
    private final int port;

    private String username;

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

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String sendAuthCommand(String login, String password) {
        try {
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

    public synchronized String sendRegCommand(String username, String login, String password) {
        try {
            Command regCommand = Command.regCommand(username, login, password);
            outputStream.writeObject(regCommand);

            Command command = readCommand();
            if (command == null) {
                return "Ошибочная команда с сервера";
            }
            switch (command.getType()) {
                case REG_OK: {
                    Main.showNetworkError("Успешная регистрация", "Успешная регистрация");
                    return null;
                }
                case REG_ERROR: {
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

    public synchronized String sendFileInfo(int fileSize, String fileName, String filePath) throws IOException {
        Command fileCommand = Command.sendInfoFile(fileSize, fileName);
        sendCommand(fileCommand);
        Command command = readCommand();
        if (command == null) {
            return "Ошибочная команда с сервера";
        }
        switch (command.getType()) {
            case STATUS_FILE: {
                InputStream is = new FileInputStream(filePath);

                int read;
                byte[] buffer = new byte[1024];
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                while (fileSize > 0) {
                    read = is.read(buffer);
                    bos.write(buffer, 0, read);
                    bos.flush();
                    fileSize -= read;
                }
                is.close();
                return null;
            }
            default:
                return "Неизвестная команда: " + command.getType();
        }
    }

    public void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
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
