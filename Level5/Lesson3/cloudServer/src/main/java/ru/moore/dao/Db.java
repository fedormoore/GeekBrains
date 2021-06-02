package ru.moore.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Db {

    public static List<Connection> connectionList = Collections.synchronizedList(new ArrayList<Connection>());

    public static Connection connection = null;

    public static Connection connection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");

        } catch (ClassNotFoundException | SQLException e) {

            return null;
        }


        return connection;
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

        } finally {
            closedStatementResult(statement, generatedKeys);
        }
    }

    public void closedStatementResult(PreparedStatement statement, ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ignored) {

            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ignored) {

            }
        }
    }

    public static void disconnect() throws SQLException {
        for (Connection dbConnect : connectionList) {
            dbConnect.close();
        }
    }
}
