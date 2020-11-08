package ru.moore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    Button regButton;

    private Network network;
    private Main clientApp;
    private Stage regDialogStage;
    private Stage authDialogStage;

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

    @FXML
    public void executeReg(ActionEvent actionEvent) {
        authDialogStage.close();
        regDialogStage.show();
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setClientApp(Main clientApp) {
        this.clientApp = clientApp;
    }

    public void setRegDialogStage(Stage regDialogStage) {
        this.regDialogStage = regDialogStage;
    }

    public void setAuthDialogStage(Stage authDialogStage) {
        this.authDialogStage = authDialogStage;
    }
}
