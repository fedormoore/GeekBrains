package ru.moore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.moore.Main;
import ru.moore.Network;

public class AuthDialogController {
    private @FXML
    TextField loginField;
    private @FXML
    PasswordField passwordField;
    private @FXML
    Button authButton;
    private @FXML
    Label timeOut;

    private Network network;
    private Main clientApp;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            Main.showNetworkError("Логин и пароль не заполнены!", "Ошибка аунтификации");
            return;
        }

        String authError = network.sendAuthCommand(login, password);
        if (authError == null) {
            clientApp.openChat();
        } else {
            Main.showNetworkError(authError, "Ошибка аунтификации");
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setClientApp(Main clientApp) {
        this.clientApp = clientApp;
    }

    public void setTime(Integer second) {
        if (second == 0) {
            timeOut.setText("Время на авторизацию истекло");
        } else {
            timeOut.setText("Сервер будет ждать " + second + " сек.");
        }
    }
}
