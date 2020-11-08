package ru.moore.server.chat;

import ru.moore.clientserver.Command;
import ru.moore.server.chat.handler.ClientHandler;
import ru.moore.server.repositories.UsersRepository;
import ru.moore.server.repositories.impl.UsersRepositoryImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MyServer implements Runnable{

    private final ServerSocket serverSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final UsersRepository usersRepository;

    public MyServer(int port, Connection connection) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.usersRepository = new UsersRepositoryImpl(connection);
    }

    @Override
    public void run() {
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        System.out.println("Сервер был запущен");

        try {
            while (true) {
                System.out.println("Ожидание нового подключения....");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился");
                processClientConnection(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Не удалось принять новое соединение");
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    private void processClientConnection(Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket, usersRepository);
        clientHandler.handle();
    }

    public synchronized void broadcastMessage(ClientHandler sender, Command command) throws IOException {
        for (ClientHandler client : clients) {
            if (client == sender) {
                continue;
            }
            client.sendMessage(command);
        }
    }

    public synchronized void sendPrivateMessage(String recipient, Command command) throws IOException {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(recipient)) {
                client.sendMessage(command);
            }
        }
    }

    public synchronized void subscribe(ClientHandler handler) throws IOException {
        clients.add(handler);
        List<String> userNames = getAllUserNames();
        broadcastMessage(null, Command.updateUsersListCommand(userNames));
    }

    public synchronized void unsubscribe(ClientHandler handler) throws IOException {
        clients.remove(handler);
        List<String> userNames = getAllUserNames();
        broadcastMessage(null, Command.updateUsersListCommand(userNames));
    }

    public synchronized void changeUserName() throws IOException {
        List<String> userNames = getAllUserNames();
        broadcastMessage(null, Command.updateUsersListCommand(userNames));
    }

    private List<String> getAllUserNames() {
        List<String> userNames = new ArrayList<>();
        for (ClientHandler client : clients) {
            userNames.add(client.getUsername());
        }
        return userNames;
    }

    public synchronized boolean isNicknameAlreadyBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
