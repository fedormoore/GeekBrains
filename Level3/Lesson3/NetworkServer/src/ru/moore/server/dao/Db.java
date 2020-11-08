package ru.moore.server.dao;

import java.sql.*;

public class Db {

    public Connection connection = null;

    public Connection connection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }


        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            throw new IllegalStateException(e);
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
}
