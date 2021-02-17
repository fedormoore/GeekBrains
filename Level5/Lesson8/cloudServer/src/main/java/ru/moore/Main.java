package ru.moore;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.moore.dao.Db;
import ru.moore.services.MyServer;

import java.io.IOException;
import java.sql.Connection;

public class Main {

    private static final int DEFAULT_PORT = 8190;

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Db db = new Db();
        Connection connection = db.connection();
        db.createDb();

        try {
            new MyServer(DEFAULT_PORT, db, connection);
        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }
    }

}
