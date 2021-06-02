package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ActiveController {

    @FXML
    private Button uploadButton;

    @FXML
    public void uploadButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Подгрузить из КРИСТЫ");
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        
    }

}
