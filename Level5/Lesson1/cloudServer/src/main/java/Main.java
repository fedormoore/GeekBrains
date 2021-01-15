import dao.Db;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import services.MyServer;

import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int DEFAULT_PORT = 8190;

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Db db = new Db();
        Connection connection = db.connection();
        db.createDb();

        int port = DEFAULT_PORT;
        if (args.length != 0) {
            port = Integer.parseInt(args[0]);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        try {
            executorService.execute(new MyServer(port, connection));
        } catch (IOException e) {
            logger.error("Невозможно запустить сервер");
            System.exit(1);
        }
    }

}
