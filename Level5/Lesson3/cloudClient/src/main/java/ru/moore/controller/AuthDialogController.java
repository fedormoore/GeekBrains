package ru.moore.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.moore.Command;
import ru.moore.Main;
import ru.moore.commands.AuthErrorCommandData;
import ru.moore.commands.AuthOkCommandData;
import ru.moore.network.Network;

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

    private Stage regDialogStage;
    private Stage authDialogStage;
    private Stage activeController;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            Main.showNetworkError("Логин и пароль не заполнены!", "Ошибка");
            return;
        }
        network.onCommand(this::onCommandSuccess);
        network.sendAuthCommand(loginField.getText(), passwordField.getText());
    }

    public void onCommandSuccess(Command networkCommand) {
        Command command = (Command) networkCommand;
        switch (command.getType()) {
            case AUTH_OK: {
                AuthOkCommandData data = (AuthOkCommandData) command.getData();
                Platform.runLater(() -> {
                    Main.username=data.getUsername();
                    authDialogStage.close();
                    activeController.show();
                });
                break;
            }
            case AUTH_ERROR: {
                AuthErrorCommandData data = (AuthErrorCommandData) command.getData();
                Platform.runLater(() -> {
                    Main.showNetworkError(data.getErrorMessage(), "Ошибка");
                });
                break;
            }
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

    public void setRegDialogStage(Stage regDialogStage) {
        this.regDialogStage = regDialogStage;
    }

    public void setAuthDialogStage(Stage authDialogStage) {
        this.authDialogStage = authDialogStage;
    }

    public void setActiveDialogStage(Stage activeController) {
        this.activeController = activeController;
    }
}
