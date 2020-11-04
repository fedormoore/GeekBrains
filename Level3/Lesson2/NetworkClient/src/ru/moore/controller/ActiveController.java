package ru.moore.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ru.moore.Main;
import ru.moore.Network;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ActiveController {

    @FXML
    public ListView<String> usersList;
    @FXML
    private Button sendMessageButton;
    @FXML
    private TextArea chatList;
    @FXML
    private TextField textMessage;
    @FXML
    private TextField userNameField;

    private Network network;

    private String selectedRecipient;

    @FXML
    private void initialize() {
        usersList.setItems(FXCollections.observableArrayList(Main.USERS_TEST_DATA));

        sendMessageButton.setOnAction(event -> sendMessage());
        textMessage.setOnAction(event -> sendMessage());

        usersList.setCellFactory(lv -> {
            MultipleSelectionModel<String> selectionModel = usersList.getSelectionModel();
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                usersList.requestFocus();
                if (! cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (selectionModel.getSelectedIndices().contains(index)) {
                        selectionModel.clearSelection(index);
                        selectedRecipient = null;
                    } else {
                        selectionModel.select(index);
                        selectedRecipient = cell.getItem();
                    }
                    event.consume();
                }
            });
            return cell ;
        });
    }

    @FXML
    public void executeSaveUserName(ActionEvent actionEvent) {
        String userName = userNameField.getText();
        if (userName == null || userName.isBlank()) {
            Main.showNetworkError("Имя пользователя не заполнены!", "Ошибка аунтификации");
            return;
        }

        String authError = network.sendUserNameCommand(userName);
        if (authError != null) {
            Main.showNetworkError(authError, "Ошибка");
        }
    }

    private void sendMessage() {
        String message = textMessage.getText();
        appendMessage("Я: " + message);
        textMessage.clear();

        try {
            if (selectedRecipient != null) {
                network.sendPrivateMessage(message, selectedRecipient);
            }
            else {
                network.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            String errorMessage = "Ошибка отправки сообщения";
            Main.showNetworkError(e.getMessage(), errorMessage);
        }
    }

    public void setNetwork(Network network) {
        this.network = network;

    }

    public void appendMessage(String message) {
        String timestamp = DateFormat.getInstance().format(new Date());
        chatList.appendText(timestamp);
        chatList.appendText(System.lineSeparator());
        chatList.appendText(message);
        chatList.appendText(System.lineSeparator());
        chatList.appendText(System.lineSeparator());
    }

    public void showError(String title, String message) {
        Main.showNetworkError(message, title);
    }

    public void updateUsers(List<String> users) {
        usersList.setItems(FXCollections.observableArrayList(users));
    }

    public void setUserName(String userName) {
        userNameField.setText(userName);
    }
}
