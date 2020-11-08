package ru.moore.server;

import ru.moore.server.chat.MyServer;
import ru.moore.server.dao.Db;

import java.io.IOException;
import java.sql.Connection;

public class ServerApp {

    private static final int DEFAULT_PORT = 8190;

    public static void main(String[] args) {
        Db db = new Db();
        Connection connection = db.connection();
        db.createDb();

        int port = DEFAULT_PORT;
        if (args.length != 0) {
            port = Integer.parseInt(args[0]);
        }
        try {
            new MyServer(port, connection).start();
        } catch (IOException e) {
            System.err.println("Невозможно запустить сервер");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
