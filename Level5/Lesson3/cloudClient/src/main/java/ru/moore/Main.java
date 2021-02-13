package ru.moore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.moore.controller.ActiveController;
import ru.moore.controller.AuthDialogController;
import ru.moore.controller.RegDialogController;
import ru.moore.network.Network;

import java.io.IOException;

public class Main extends Application {

    private Network network;
    private ActiveController activeController;
    private AuthDialogController authController;
    private RegDialogController regController;
    private Stage activeStage;
    private Stage authDialogStage;
    private Stage regDialogStage;

    public static String username;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.activeStage = primaryStage;

        network = new Network();

        fxmlLoaderReg(activeStage);
        fxmlLoaderAuth(activeStage);

        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(Main.class.getResource("/activeForm.fxml"));
        Parent root = mainLoader.load();

        primaryStage.setTitle("Облако: "+username);
        primaryStage.setScene(new Scene(root, 600, 400));

        activeController = mainLoader.getController();
        activeController.setNetwork(network);
    }

    private void fxmlLoaderReg(Stage primaryStage) throws IOException {
        FXMLLoader regLoader = new FXMLLoader();
        regLoader.setLocation(getClass().getResource("/regDialog.fxml"));
        Parent regDialogPanel = regLoader.load();

        regDialogStage = new Stage();

        regDialogStage.setTitle("Регистрация пользователя");
        regDialogStage.initModality(Modality.WINDOW_MODAL);
        regDialogStage.initOwner(primaryStage);
        Scene scene = new Scene(regDialogPanel);
        regDialogStage.setScene(scene);

        regController = regLoader.getController();
        regController.setNetwork(network);
        regController.setRegDialogStage(regDialogStage);
    }

    private void fxmlLoaderAuth(Stage primaryStage) throws IOException {
        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(Main.class.getResource("/authDialog.fxml"));
        Parent authDialogPanel = authLoader.load();

        authDialogStage = new Stage();

        authDialogStage.setTitle("Аутентификая");
        authDialogStage.initModality(Modality.WINDOW_MODAL);
        authDialogStage.initOwner(primaryStage);
        Scene scene = new Scene(authDialogPanel);
        authDialogStage.setScene(scene);
        authDialogStage.show();

        authController = authLoader.getController();
        authController.setNetwork(network);
        authController.setRegDialogStage(regDialogStage);
        authController.setAuthDialogStage(authDialogStage);
        authController.setActiveDialogStage(activeStage);

        regController.setAuthDialogStage(authDialogStage);
    }

    public static void showNetworkError(String errorDetails, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка сети");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorDetails);
        alert.showAndWait();
    }

}
