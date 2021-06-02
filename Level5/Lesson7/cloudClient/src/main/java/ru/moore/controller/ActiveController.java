package ru.moore.controller;

import io.netty.util.internal.SystemPropertyUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.moore.Command;
import ru.moore.Main;
import ru.moore.commands.DirectoryCommandData;
import ru.moore.network.Network;
import ru.moore.utils.FileItem;
import ru.moore.utils.FilePart;
import ru.moore.utils.FileTreeItem;
import ru.moore.utils.FilesList;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ActiveController implements Initializable {

    public static final Path BASE_PATH = Paths.get(SystemPropertyUtil.get("user.dir") + "/client/userDir");

    @FXML
    private MenuItem menuCreateFolder;
    @FXML
    private MenuItem menuRenameFolder;
    @FXML
    private MenuItem menuDeleteFolder;
    @FXML
    private MenuItem menuRenameFiles;
    @FXML
    private MenuItem menuDownloadFiles;
    @FXML
    private MenuItem menuDeleteFile;
    @FXML
    private MenuItem menuUploadFile;
    @FXML
    public TreeView<FileTreeItem> treeView;
    @FXML
    public TableView<FileItem> tableView;

    private final TreeItem<FileTreeItem> rootNode = new TreeItem<>(new FileTreeItem("Сервер", ""));

    private String currentFolder = "./";
    private String folder = "./";
    private String currentFile;

    private static final Logger logger = LogManager.getLogger(ActiveController.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Network.getNetwork().onCommand(this::onCommandSuccess);
        Network.getNetwork().getDirectory();
        treeView.setCellFactory(new Callback<TreeView<FileTreeItem>, TreeCell<FileTreeItem>>() {
            @Override
            public TreeCell<FileTreeItem> call(TreeView<FileTreeItem> tree) {
                TreeCell<FileTreeItem> cell = new TreeCell<FileTreeItem>() {
                    @Override
                    public void updateItem(FileTreeItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.toString());
                        }
                    }
                };
                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (cell.isEmpty()) {
                            menuCreateFolder.setVisible(false);
                            menuRenameFolder.setVisible(false);
                            menuDeleteFolder.setVisible(false);
                        } else {
                            if (cell.getText().equals("Сервер")) {
                                menuCreateFolder.setVisible(true);
                                menuRenameFolder.setVisible(false);
                                menuDeleteFolder.setVisible(false);
                            } else {
                                menuCreateFolder.setVisible(true);
                                menuRenameFolder.setVisible(true);
                                menuDeleteFolder.setVisible(true);
                            }
                            currentFolder = cell.getTreeItem().getValue().getPath();
                            folder = cell.getText();
                            Network.getNetwork().getFiles(currentFolder);
                        }
                    }
                });
                return cell;
            }
        });
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileItem person = tableView.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 2 && (person != null)) {
                    currentFile = person.getName();
                    openFiles();
                }
                if (person == null) {
                    menuRenameFiles.setVisible(false);
                    menuDownloadFiles.setVisible(false);
                    menuDeleteFile.setVisible(false);
                } else {
                    currentFile = person.getName();
                    menuRenameFiles.setVisible(true);
                    menuDownloadFiles.setVisible(true);
                    menuDeleteFile.setVisible(true);
                }
            }
        });
        initTableView();
    }

    @FXML
    private void createFolder(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Создать папку");
        dialog.setHeaderText("Введите имя новой папки");
        dialog.setContentText("Имя папки:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(new Consumer<String>() {
            @Override
            public void accept(String newName) {
                Network.getNetwork().createFolder(currentFolder + "/" + newName);
            }
        });
    }

    @FXML
    private void renameFolder(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Переименовать папку");
        dialog.setHeaderText("Введите имя папки");
        dialog.setContentText("Имя папки:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> Network.getNetwork().renameFolder(currentFolder, currentFolder.substring(0, currentFolder.length() - folder.length()) + newName));
    }

    @FXML
    private void deleteFolder(ActionEvent event) {
        Network.getNetwork().deleteFolder(currentFolder);
    }

    @FXML
    private void renameFiles(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog(currentFile);
        dialog.setTitle("Переименовать файл");
        dialog.setHeaderText("Введите имя файла");
        dialog.setContentText("Имя файла:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> Network.getNetwork().renameFile(currentFolder, currentFile, newName));
    }

    @FXML
    private void downloadFiles(ActionEvent event) {
        downloadFile();
    }

    @FXML
    private void deleteFile(ActionEvent event) {
        Path path = Paths.get(BASE_PATH + "/" + currentFolder + "/" + currentFile);
        path.normalize();
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                logger.error(e);
            }
        }
        Network.getNetwork().deleteFile(currentFolder + "/" + currentFile);
    }

    @FXML
    private void uploadFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            Network.getNetwork().uploadFile(file.getAbsolutePath(), currentFolder);
        }
    }

    private void openFiles() {
        if (Files.exists(Path.of(BASE_PATH + "/" + currentFolder + "/" + currentFile))) {
            try {
                Desktop.getDesktop().open(Paths.get(BASE_PATH + "/" + currentFolder + "/" + currentFile).toFile());
            } catch (IOException e) {
                logger.error(e);
                Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось открыть файл");
                alert.show();
            }
        } else {
            downloadFile();
        }
    }

    private void downloadFile() {
        Path path = Path.of(BASE_PATH + "/" + currentFolder);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                logger.error(e);
            }
        }
        Network.getNetwork().downloadFile(Path.of(currentFolder, currentFile).toString());
    }

    private void initTableView() {
        TableColumn<FileItem, String> columnFileName = new TableColumn<>("Имя");
        columnFileName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<FileItem, Long> columnSize = new TableColumn<>("Размер");
        columnSize.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<FileItem, Boolean> columnExist = new TableColumn<>("Скачан");
        columnExist.setCellValueFactory(new PropertyValueFactory<>("exist"));

        tableView.getColumns().setAll(columnFileName, columnSize, columnExist);
    }

    private void updateTreeView(DirectoryCommandData directoryTree) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rootNode.getChildren().clear();
                for (FileTreeItem fileTreeItem : directoryTree.getChildren()) {
                    String itemPath = fileTreeItem.getPath();
                    String[] pathParts = itemPath.replaceAll("\\\\", "/").split("/");
                    if (pathParts.length == 1) {
                        rootNode.getChildren().add(new TreeItem<>(fileTreeItem));
                    } else {
                        String parentName;
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < pathParts.length - 1; i++) {
                            sb.append(pathParts[i]).append("/");
                        }
                        sb.deleteCharAt(sb.lastIndexOf("/"));
                        parentName = sb.toString();
                        TreeItem<FileTreeItem> parent = ActiveController.this.findItemByPath(rootNode, parentName);
                        ObservableList<TreeItem<FileTreeItem>> children;
                        if (parent != null) {
                            children = parent.getChildren();
                            if (children != null) {
                                children.add(new TreeItem<>(fileTreeItem));
                            }
                        }
                    }
                }
                treeView.setRoot(rootNode);
                treeView.setShowRoot(true);
                treeView.setEditable(false);
            }
        });
    }

    private TreeItem<FileTreeItem> findItemByPath(TreeItem<FileTreeItem> root, String parentName) {
        if (root.getValue().getPath().equals(parentName)) {
            return root;
        }
        for (TreeItem<FileTreeItem> child : root.getChildren()) {
            TreeItem<FileTreeItem> item = findItemByPath(child, parentName);
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    private void updateFileTable(FilesList filesList) {
        for (FileItem fileItem : filesList.getList()) {
            fileItem.setExist(Files.exists(BASE_PATH.resolve(currentFolder).resolve(fileItem.getName())));
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateTableView(filesList);
            }
        });
    }

    private void updateTableView(FilesList filesList) {
        tableView.getItems().clear();
        if (filesList != null) {
            SortedList<FileItem> sortedList = new SortedList<>(
                    FXCollections.observableArrayList(filesList.getList())
            );
            sortedList.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.getItems().addAll(sortedList);
        }
    }

    private void writeFilePart(FilePart data) {
        if (data.isEnd()) {
            Platform.runLater(() -> {
                Network.getNetwork().getFiles(currentFolder);
                Main.showNetworkError("Файл скачан", "Информация");
            });
        } else {
            Path filePath = Path.of(BASE_PATH + "/" + currentFolder + "/" + data.getFileName());
            try {
                Files.write(filePath, data.getData(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {

            }
        }
    }

    void onCommandSuccess(Command networkCommand) {
        Command command = (Command) networkCommand;
        switch (command.getType()) {
            case SET_DIR: {
                updateTreeView((DirectoryCommandData) command.getData());
                break;
            }
            case CREATE_FOLDER:
            case DELETE_FOLDER:
            case RENAME_FOLDER: {
                Network.getNetwork().getDirectory();
                break;
            }
            case SET_FILES: {
                updateFileTable((FilesList) command.getData());
                break;
            }
            case FILE_UPLOADER: {
                writeFilePart((FilePart) command.getData());
                break;
            }
            case FILE_UPLOADER_RESULT:
            case RENAME_FILE:
            case DELETE_FILE: {
                Network.getNetwork().getFiles(currentFolder);
                break;
            }
        }

    }

}
