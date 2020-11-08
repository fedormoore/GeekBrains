package ru.moore.server.chat.auth;

import ru.moore.server.chat.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseAuthService implements AuthService {

    private static final List<User> USERS = List.of(
            new User("login1", "pass1", "Oleg"),
            new User("login2", "pass2", "Alexey"),
            new User("login3", "pass3", "Peter")
    );

    @Override
    public void start() {
        System.out.println("Сервис аунтификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Auth service has been finished");
    }

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for (User user : USERS) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user.getUsername();
            }
        }

        return null;
    }
}
