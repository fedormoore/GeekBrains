package ru.moore.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.moore.Command;
import ru.moore.Main;
import ru.moore.commands.AuthErrorCommandData;
import ru.moore.commands.AuthOkCommandData;
import ru.moore.network.Network;

import java.io.IOException;

public class AuthDialogController {

    private @FXML
    TextField loginField;
    private @FXML
    PasswordField passwordField;
    private @FXML
    Button authButton;
    private @FXML
    Button regButton;

    private Stage authDialogStage;

    private static final Logger logger = LogManager.getLogger(AuthDialogController.class.getName());

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            Main.showNetworkError("Логин и пароль не заполнены!", "Ошибка");
            return;
        }
        Network.getNetwork().onCommand(this::onCommandSuccess);
        Network.getNetwork().sendAuthCommand(loginField.getText(), passwordField.getText());
    }

    public void onCommandSuccess(Command networkCommand) {
        Command command = (Command) networkCommand;
        switch (command.getType()) {
            case AUTH_OK: {
                AuthOkCommandData data = (AuthOkCommandData) command.getData();
                Platform.runLater(() -> {
                    Main.username = data.getUsername();
                    authDialogStage.close();

                    FXMLLoader activeLoader = new FXMLLoader();
                    activeLoader.setLocation(getClass().getResource("/activeForm.fxml"));

                    Parent activeDialogPanel = null;
                    try {
                        activeDialogPanel = activeLoader.load();
                    } catch (IOException e) {
                        logger.error(e);
                    }

                    Stage activeDialogStage = new Stage();
                    activeDialogStage.setTitle("Облако: " + Main.username);
                    activeDialogStage.initModality(Modality.NONE);
                    activeDialogStage.setResizable(true);
                    Scene scene = new Scene(activeDialogPanel);
                    activeDialogStage.setScene(scene);
                    activeDialogStage.setOnCloseRequest(value -> Network.getNetwork().close());

                    activeDialogStage.show();
                });
                break;
            }
            case AUTH_ERROR: {
                AuthErrorCommandData data = (AuthErrorCommandData) command.getData();
                Platform.runLater(() -> {
                    Main.showNetworkError(data.getErrorMessage(), "Ошибка");
                    logger.error(data.getErrorMessage());
                });
                break;
            }
        }
    }

    @FXML
    public void executeReg(ActionEvent actionEvent) {
        authDialogStage.close();

        FXMLLoader regLoader = new FXMLLoader();
        regLoader.setLocation(getClass().getResource("/regDialog.fxml"));
        Parent regDialogPanel = null;
        try {
            regDialogPanel = regLoader.load();
        } catch (IOException e) {
            logger.error(e);
        }

        Stage regDialogStage = new Stage();
        regDialogStage.setTitle("Регистрация пользователя");
        regDialogStage.initModality(Modality.WINDOW_MODAL);
        regDialogStage.setResizable(false);
        Scene scene = new Scene(regDialogPanel);
        regDialogStage.setScene(scene);
        regDialogStage.setOnCloseRequest(value -> Network.getNetwork().close());

        regDialogStage.show();

        RegDialogController regController = regLoader.getController();
        regController.setAuthDialogStage(authDialogStage);
        regController.setRegDialogStage(regDialogStage);
    }

    public void setAuthDialogStage(Stage authDialogStage) {
        this.authDialogStage = authDialogStage;
    }

}
