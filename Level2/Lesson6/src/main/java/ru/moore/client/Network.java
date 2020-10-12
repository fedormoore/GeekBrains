package ru.moore.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network extends Thread{

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8189;

    private final String host;
    private final int port;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public Network() {
        this(SERVER_ADDRESS, SERVER_PORT);
    }

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port)) {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String message = inputStream.readUTF();
                System.out.println("Server message: " + message);
                if (message.equals("/end")) {
                    outputStream.writeUTF("/end");
                    break;
                }
            }
            close();
            System.out.println("Client has been closed");
        } catch (IOException e) {
            System.err.println("Соединение не было установлено!");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
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
