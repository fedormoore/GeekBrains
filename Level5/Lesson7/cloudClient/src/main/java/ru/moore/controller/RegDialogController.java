package ru.moore.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.moore.Command;
import ru.moore.Main;
import ru.moore.commands.RegErrorCommandData;
import ru.moore.commands.RegOkCommandData;
import ru.moore.network.Network;

public class RegDialogController {

    private @FXML
    TextField userNameField;
    private @FXML
    TextField loginField;
    private @FXML
    PasswordField passwordField;

    private Stage authDialogStage;
    private Stage regDialogStage;

    private static final Logger logger = LogManager.getLogger(RegDialogController.class.getName());

    @FXML
    public void executeReg(ActionEvent actionEvent) {
        if (userNameField.getText().isEmpty() || loginField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            Main.showNetworkError("Не все поля заполнены", "Ошибка");
            return;
        }
        Network.getNetwork().onCommand(this::onCommandSuccess);
        Network.getNetwork().sendRegCommand(userNameField.getText(), loginField.getText(), passwordField.getText());
    }

    public void onCommandSuccess(Command networkCommand) {
        Command command = (Command) networkCommand;
        switch (command.getType()) {
            case REG_OK: {
                RegOkCommandData data = (RegOkCommandData) command.getData();
                Platform.runLater(() -> {
                    Main.showNetworkError(data.getStatus(), "Сообщение");
                    regDialogStage.close();
                    authDialogStage.show();
                });
                break;
            }
            case REG_ERROR: {
                RegErrorCommandData data = (RegErrorCommandData) command.getData();
                Platform.runLater(() -> {
                    Main.showNetworkError(data.getErrorMessage(), "Ошибка");
                    logger.error(data.getErrorMessage());
                });
                break;
            }
        }
    }

    public void setAuthDialogStage(Stage authDialogStage) {
        this.authDialogStage = authDialogStage;
    }

    public void setRegDialogStage(Stage regDialogStage) {
        this.regDialogStage = regDialogStage;
    }

}
