package services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import repositories.UsersRepository;
import repositories.impl.UsersRepositoryImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MyServer implements Runnable{

    private static final Logger logger = LogManager.getLogger(MyServer.class.getName());

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
        logger.info("Сервер запущен");
        try {
            while (true) {
                logger.info("Ожидание нового подключения....");
                Socket clientSocket = serverSocket.accept();
                logger.info("Клиент подключился");
                processClientConnection(clientSocket);
            }
        } catch (IOException e) {
            logger.error("Не удалось принять новое соединение");
        } finally {
            serverSocket.close();
        }
    }

    private void processClientConnection(Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket, usersRepository);
        clientHandler.handle();
    }

    public synchronized void subscribe(ClientHandler handler) throws IOException {
        clients.add(handler);
    }

    public synchronized void unsubscribe(ClientHandler handler) throws IOException {
        clients.remove(handler);
    }
}
