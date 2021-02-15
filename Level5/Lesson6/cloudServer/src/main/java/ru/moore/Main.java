package ru.moore;

import ru.moore.dao.Db;
import ru.moore.services.MyServer;

import java.io.IOException;
import java.sql.Connection;

public class Main {

    private static final int DEFAULT_PORT = 8190;

    public static void main(String[] args) {
        Db db = new Db();
        Connection connection = db.connection();
        db.createDb();

        try {
            new MyServer(DEFAULT_PORT, db, connection);
        } catch (IOException e) {
            System.exit(1);
        }
    }

}
