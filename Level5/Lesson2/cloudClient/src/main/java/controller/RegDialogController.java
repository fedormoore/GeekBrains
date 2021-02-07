package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegDialogController {

    private @FXML
    TextField userNameField;
    private @FXML
    TextField loginField;
    private @FXML
    PasswordField passwordField;

    private Stage authDialogStage;
    private Stage regDialogStage;

    @FXML
    public void executeReg(ActionEvent actionEvent) {

    }

    public void setAuthDialogStage(Stage authDialogStage) {
        this.authDialogStage = authDialogStage;
    }

    public void setRegDialogStage(Stage regDialogStage) {
        this.regDialogStage = regDialogStage;
    }

}
