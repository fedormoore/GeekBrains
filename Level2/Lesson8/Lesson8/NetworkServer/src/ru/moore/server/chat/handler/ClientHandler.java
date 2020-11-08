package ru.moore.server.chat.handler;

import ru.moore.clientserver.Command;
import ru.moore.clientserver.CommandType;
import ru.moore.clientserver.commands.AuthCommandData;
import ru.moore.clientserver.commands.PrivateMessageCommandData;
import ru.moore.clientserver.commands.PublicMessageCommandData;
import ru.moore.server.chat.MyServer;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler {

    private static final Integer TIME_OUT = 120000;

    private final MyServer myServer;
    private final Socket clientSocket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private String username;

    private Timer outTimer;
    private OutTimerTask outTimerTask;
    private Integer runTimer = TIME_OUT / 1000;

    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.myServer = myServer;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        in = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                Timer mTimer = startTimer();
                authentication();
                outTimer.cancel();
                outTimerTask.cancel();
                mTimer.cancel();
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
        return username;
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
                    myServer.sendPrivateMessage(recipient, Command.messageInfoCommand(privateMessage, username));
                    break;
                }
                case PUBLIC_MESSAGE: {
                    PublicMessageCommandData data = (PublicMessageCommandData) command.getData();
                    String message = data.getMessage();
                    String sender = data.getSender();
                    myServer.broadcastMessage(this, Command.messageInfoCommand(message, sender));
                    break;
                }
                default:
                    System.err.println("Неизвестный тип команды: " + command.getType());
            }
        }
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

    private void authentication() throws IOException {
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }

            if (command.getType() == CommandType.AUTH) {
                boolean isSuccessAuth = processAuthCommand(command);
                if (isSuccessAuth) {
                    break;
                }
            } else {
                sendMessage(Command.authErrorCommand("Аунтификация прошла с ошибкой!"));
            }
        }
    }

    private boolean processAuthCommand(Command command) throws IOException {
        AuthCommandData cmdData = (AuthCommandData) command.getData();
        String login = cmdData.getLogin();
        String password = cmdData.getPassword();
        this.username = myServer.getAuthService().getUsernameByLoginAndPassword(login, password);
        if (username != null) {
            if (myServer.isNicknameAlreadyBusy(username)) {
                sendMessage(Command.authErrorCommand("Участник уже в чате!"));
                return false;
            }
            sendMessage(Command.authOkCommand(username));
            String message = username + " приветствуем в чате!";
            myServer.broadcastMessage(this, Command.messageInfoCommand(message, null));
            myServer.subscribe(this);
            return true;
        } else {
            sendMessage(Command.authErrorCommand("Неверный логин или пароль!"));
            return false;
        }
    }

    private void closeConnection() throws IOException {
        myServer.unsubscribe(this);
        clientSocket.close();
    }

    public void sendMessage(Command command) throws IOException {
        out.writeObject(command);
    }

    private Timer startTimer() {
        outTimer = new Timer();
        outTimerTask = new OutTimerTask();
        outTimer.schedule(outTimerTask, 1, 1000);

        Timer mTimer = new Timer();
        CloseConnectionTask closeConnectionTask = new CloseConnectionTask();
        mTimer.schedule(closeConnectionTask, TIME_OUT);
        return mTimer;
    }

    class CloseConnectionTask extends TimerTask {
        @Override
        public void run() {
            try {
                closeConnection();
            } catch (IOException e) {
                System.err.println("Failed to close connection!");
            }
        }
    }

    class OutTimerTask extends TimerTask {
        @Override
        public void run() {
            runTimer--;
            try {
                //out.writeUTF(String.format("%s %s", TIMER_CMD_PREFIX, runTimer));
                sendMessage(Command.timeLeft(runTimer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
