package ru.moore.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Network extends Thread {

    private static final int SERVER_PORT = 8189;

    private final int port;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public Network() {
        this(SERVER_PORT);
    }

    public Network(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Ожидание клиента");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился!");

            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                String message = inputStream.readUTF();
                System.out.println("Client message: " + message);
                if (message.equals("/end")) {
                    outputStream.writeUTF("/end");
                    break;
                }
            }
            close();
            System.out.println("Server has been closed");
        } catch (IOException e) {
            System.err.println("Server port is already opened!");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to send message");
        }
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
