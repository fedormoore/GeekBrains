package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.Main;

public class AuthDialogController {

    private @FXML
    TextField loginField;
    private @FXML
    PasswordField passwordField;
    private @FXML
    Button authButton;
    private @FXML
    Button regButton;

    private Stage regDialogStage;
    private Stage authDialogStage;

    private Main clientApp;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {

    }

    @FXML
    public void executeReg(ActionEvent actionEvent) {
        authDialogStage.close();
        regDialogStage.show();
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
