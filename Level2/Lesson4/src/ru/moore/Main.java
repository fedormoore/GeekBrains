package ru.moore;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import ru.moore.controller.ActiveController;
import ru.moore.model.Person;
import ru.moore.repository.PersonRepository;
import ru.moore.repository.impl.PersonRepositoryImpl;

public class Main extends Application {

    private final ObservableList<Person> personData;

    public Main() {
        PersonRepository personRepository = new PersonRepositoryImpl();
        personData = FXCollections.observableArrayList(personRepository.getAllData());
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/activeForm.fxml"));

        Parent root = loader.load();

        ActiveController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage = primaryStage;
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
