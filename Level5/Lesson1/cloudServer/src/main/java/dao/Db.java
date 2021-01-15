package dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;

public class Db {

    private static final Logger logger = LogManager.getLogger(Db.class.getName());

    public Connection connection = null;

    public Connection connection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");

        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Невозможно подключитбься к базе данных");
            return null;
        }


        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Ошибка закрытия подключения к базе данных");
        }
    }

    public void createDb() {
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            statement = connection.prepareStatement("create table if not exists users (" +
                    "id integer primary key autoincrement not null, " +
                    "username text not null, " +
                    "login text not null, " +
                    "password text not null)");
            statement.execute();
        } catch (SQLException e) {
            logger.error("Ошибка создания базы данных");
        } finally {
            closedStatementResult(statement, generatedKeys);
        }
    }

    public void closedStatementResult(PreparedStatement statement, ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ignored) {
                logger.error("Ошибка закрытия statement");
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ignored) {
                logger.error("Ошибка закрытия resultSet");
            }
        }
    }
}
