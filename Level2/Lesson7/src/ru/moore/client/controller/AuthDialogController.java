package ru.moore.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.moore.client.Main;
import ru.moore.client.Network;

public class AuthDialogController {
    private @FXML TextField loginField;
    private @FXML PasswordField passwordField;
    private @FXML Button authButton;

    private Network network;
    private Main clientApp;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            Main.showNetworkError("Username and password should be not empty!", "Auth error");
            return;
        }

        String authError = network.sendAuthCommand(login, password);
        if (authError == null) {
            clientApp.openChat();
        } else {
            Main.showNetworkError(authError, "Auth error");
        }


    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setClientApp(Main clientApp) {
        this.clientApp = clientApp;
    }
}
