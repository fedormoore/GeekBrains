package ru.moore.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ru.moore.client.Main;
import ru.moore.client.Network;
import ru.moore.client.model.Person;
import ru.moore.client.repository.PersonRepository;
import ru.moore.client.repository.impl.PersonRepositoryImpl;

import java.io.IOException;

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
    private Network network;

    private final ObservableList<Person> personData;

    @FXML
    private void initialize() {
        loginColumn.setCellValueFactory(cellData -> cellData.getValue().loginProperty());

        sendMessageButton.setOnAction(event -> sendMessage());
        textMessage.setOnAction(event -> sendMessage());

        personTable.setItems(getPersonData());
        /*personTable.setRowFactory(param -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        sendPersonMessage(personTable.getSelectionModel().getSelectedItem());
                    }
                }
            });
            return row;
        });*/
    }

//    private void sendPersonMessage(Person selectedItem) {
//        textMessage.setText("/w " + selectedItem.getLogin() + " ");
//    }

    public ActiveController() {
        PersonRepository personRepository = new PersonRepositoryImpl();
        personData = FXCollections.observableArrayList(personRepository.getAllData());
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    private void sendMessage() {
        String message = textMessage.getText();
        appendMessage("Я: " + message);
        textMessage.clear();

        try {
            Person selectedItem = personTable.getSelectionModel().getSelectedItem();
            if (!selectedItem.getLogin().equals("Отправить всем")) {
                message = "/w " + selectedItem.getLogin() + " " + message;
            }
            network.getOutputStream().writeUTF(message);
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
        chatList.appendText(message);
        chatList.appendText(System.lineSeparator());
    }

}
