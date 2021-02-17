package ru.moore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.moore.controller.AuthDialogController;
import ru.moore.network.Network;

public class Main extends Application {

    public static String username;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Network.getNetwork();

        primaryStage.setOnCloseRequest(value ->Network.getNetwork().close());

        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(Main.class.getResource("/authDialog.fxml"));
        Parent root = authLoader.load();
        Stage authDialogStage = new Stage();

        authDialogStage.setTitle("Аутентификая");
        authDialogStage.initModality(Modality.WINDOW_MODAL);
        authDialogStage.initOwner(primaryStage);
        authDialogStage.setResizable(false);
        Scene scene = new Scene(root);
        authDialogStage.setScene(scene);
        authDialogStage.setOnCloseRequest(value -> Network.getNetwork().close());
        authDialogStage.show();

        AuthDialogController authController = authLoader.getController();
        authController.setAuthDialogStage(authDialogStage);
    }

    public static void showNetworkError(String errorDetails, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка сети");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorDetails);
        alert.showAndWait();
    }

}
