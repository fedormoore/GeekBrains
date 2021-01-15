package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.Main;
import main.java.Network;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ActiveController {

    @FXML
    private Button uploadButton;

    private Network network;

    @FXML
    public void uploadButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Подгрузить из КРИСТЫ");
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                int fileSize= Files.readAllBytes(file.toPath()).length;
                String fileName=file.getName();

                String error=network.sendFileInfo(fileSize, fileName, file.toPath().toString());
                if (error == null) {
//                    regDialogStage.close();
//                    authDialogStage.show();
                } else {
                    Main.showNetworkError(error, "Ошибка отправики файла");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
/*        String username = userNameField.getText();
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
        }*/
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
}
