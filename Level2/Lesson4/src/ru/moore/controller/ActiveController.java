package ru.moore.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.moore.Main;
import ru.moore.model.Person;

public class ActiveController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> loginColumn;
    @FXML
    private Button sendMessageButton;
    @FXML
    private TextArea chatList;
    @FXML
    private TextField textMessage;
    private Main mainApp;

    @FXML
    private void initialize() {
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().loginProperty());

        sendMessageButton.setOnAction(event -> sendMessage());
        textMessage.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
        chatList.appendText(textMessage.getText());
        chatList.appendText(System.lineSeparator());
        textMessage.clear();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        personTable.setItems(mainApp.getPersonData());
    }
}
