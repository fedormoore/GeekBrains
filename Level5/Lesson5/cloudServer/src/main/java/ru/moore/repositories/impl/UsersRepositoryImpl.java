package ru.moore.repositories.impl;

import ru.moore.models.Users;
import ru.moore.repositories.UsersRepository;

import java.sql.*;

public class UsersRepositoryImpl implements UsersRepository {

    private final String SQL_INSERT_USER = "insert into users (username, login, password) values (?, ?, ?)";
    private final String SQL_FIND_USER_LOGIN = "select * from users where login = ?";
    private final String SQL_FIND_USER_LOGIN_PASSWORD = "select * from users where login = ? and password = ?";
    private final String SQL_UPDATE_USER = "update users set username=? where id = ?";

    private Connection connection;

    public UsersRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean findUserByLogin(String login) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            closedStatementResult(statement, resultSet);
        }
    }

    @Override
    public Users findUserByLoginAndPassword(String login, String password) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_USER_LOGIN_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Users(resultSet.getLong(1), resultSet.getString("username"), resultSet.getString("login"), resultSet.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            closedStatementResult(statement, resultSet);
        }
    }

    @Override
    public Users save(Users object) {
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, object.getUsername());
            statement.setString(2, object.getLogin());
            statement.setString(3, object.getPassword());
            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("User save not executed");
            }

            generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                object.setId(id);
            } else {
                throw new SQLException("Something wrong");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            closedStatementResult(statement, generatedKeys);
        }
        return object;
    }

    @Override
    public void update(Users object) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, object.getUsername());
            statement.setLong(2, object.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            closedStatementResult(statement, null);
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
