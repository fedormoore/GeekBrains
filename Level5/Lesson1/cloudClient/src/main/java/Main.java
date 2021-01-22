package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controller.ActiveController;
import main.java.controller.AuthDialogController;
import main.java.controller.RegDialogController;

public class Main extends Application {

    private Stage primaryStage;

    private Network network;
    private ActiveController activeController;
    private AuthDialogController authController;
    private RegDialogController regController;
    private Stage authDialogStage;
    private Stage regDialogStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        network = new Network();
        if (!network.connect()) {
            showNetworkError("", "Нет соединения с сервером");
        }

        fxmlLoaderReg(primaryStage);
        fxmlLoaderAuth(primaryStage);

        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(Main.class.getResource("view/activeForm.fxml"));
        Parent root = mainLoader.load();

        primaryStage.setTitle("Облако");
        primaryStage.setScene(new Scene(root, 600, 400));

        activeController = mainLoader.getController();
        activeController.setNetwork(network);

        primaryStage.setOnCloseRequest(event -> {
            network.close();
        });
    }

    private void fxmlLoaderReg(Stage primaryStage) throws java.io.IOException {
        FXMLLoader regLoader = new FXMLLoader();
        regLoader.setLocation(Main.class.getResource("view/regDialog.fxml"));
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

    private void fxmlLoaderAuth(Stage primaryStage) throws java.io.IOException {
        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(Main.class.getResource("view/authDialog.fxml"));
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
        authController.setClientApp(this);
        authController.setRegDialogStage(regDialogStage);
        authController.setAuthDialogStage(authDialogStage);

        regController.setAuthDialogStage(authDialogStage);
    }

    public static void showNetworkError(String errorDetails, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка сети");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorDetails);
        alert.showAndWait();
    }

    public void openChat() {
        authDialogStage.close();
        primaryStage.show();
        primaryStage.setTitle(network.getUsername());
        //network.setViewController(activeController);
        //network.waitMessages();
    }

}
