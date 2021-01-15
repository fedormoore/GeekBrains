package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.Main;
import main.java.Network;

public class RegDialogController {

    private @FXML
    TextField userNameField;
    private @FXML
    TextField loginField;
    private @FXML
    PasswordField passwordField;

    private Network network;
    private Stage authDialogStage;
    private Stage regDialogStage;

    @FXML
    public void executeReg(ActionEvent actionEvent) {
        String username = userNameField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();
        if (username == null || username.isBlank() || login == null || login.isBlank() || password == null || password.isBlank()) {
            Main.showNetworkError("Имя пользователя, логин или пароль не заполнены!", "Ошибка регистрации");
            return;
        }

        String authError = network.sendRegCommand(username, login, password);
        if (authError == null) {
            regDialogStage.close();
            authDialogStage.show();
        } else {
            Main.showNetworkError(authError, "Ошибка регистрации");
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setAuthDialogStage(Stage authDialogStage) {
        this.authDialogStage = authDialogStage;
    }

    public void setRegDialogStage(Stage regDialogStage) {
        this.regDialogStage = regDialogStage;
    }

}
