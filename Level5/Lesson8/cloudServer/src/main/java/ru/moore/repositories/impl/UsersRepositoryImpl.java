package ru.moore.repositories.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.moore.models.Users;
import ru.moore.repositories.UsersRepository;

import java.sql.*;

public class UsersRepositoryImpl implements UsersRepository {

    private final String SQL_INSERT_USER = "insert into users (username, login, password) values (?, ?, ?)";
    private final String SQL_FIND_USER_LOGIN = "select * from users where login = ?";
    private final String SQL_FIND_USER_LOGIN_PASSWORD = "select * from users where login = ? and password = ?";

    private Connection connection;

    private static final Logger logger = LogManager.getLogger(UsersRepositoryImpl.class.getName());

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
            logger.error(e);
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
            logger.error(e);
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
                logger.error("User save not executed");
                throw new SQLException("User save not executed");
            }

            generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                object.setId(id);
            } else {
                logger.error("Something wrong");
                throw new SQLException("Something wrong");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new IllegalStateException(e);
        } finally {
            closedStatementResult(statement, generatedKeys);
        }
        return object;
    }

    public void closedStatementResult(PreparedStatement statement, ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

}
