package ru.moore.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import ru.moore.Command;
import ru.moore.commands.DirectoryCommandData;
import ru.moore.commands.FileItem;
import ru.moore.commands.FileTreeItem;
import ru.moore.network.Network;

import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ActiveController implements Initializable {

    @FXML
    private MenuItem menuCreateFolder;
    @FXML
    private MenuItem menuRenameFolder;
    @FXML
    private MenuItem menuDeleteFolder;

    private final TreeItem<FileTreeItem> rootNode = new TreeItem<>(new FileTreeItem("Сервер", ""));

    public TreeView<FileTreeItem> treeView;

    private String currentFolder = "./";
    private String folder = "./";
//    private String currentFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Network.getNetwork().onCommand(this::onCommandSuccess);
        Network.getNetwork().getDirectory();
//        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<FileTreeItem>>() {
//            @Override
//            public void changed(ObservableValue<? extends TreeItem<FileTreeItem>> observableValue, TreeItem<FileTreeItem> oldItem, TreeItem<FileTreeItem> newItem) {
//                currentFolder = newItem.getValue().getPath();
//                currentFile = null;
//            }
//        });
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
                cell.setOnMouseClicked(event -> {
                    if (cell.isEmpty()) {

                        menuCreateFolder.setVisible(false);
                        menuRenameFolder.setVisible(false);
                        menuDeleteFolder.setVisible(false);
                    } else {
                        if (cell.getText().equals("Сервер")){
                            menuCreateFolder.setVisible(true);
                            menuRenameFolder.setVisible(false);
                            menuDeleteFolder.setVisible(false);
                        }else {
                            menuCreateFolder.setVisible(true);
                            menuRenameFolder.setVisible(true);
                            menuDeleteFolder.setVisible(true);
                        }
                        currentFolder = cell.getTreeItem().getValue().getPath();
                        folder = cell.getText();
                    }

                });
                return cell;
            }
        });
        initTableView();
        rootNode.setExpanded(true);
    }

    @FXML
    private void createFolder(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Создать папку");
        dialog.setHeaderText("Введите имя новой папки");
        dialog.setContentText("Имя папки:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> Network.getNetwork().createFolder(currentFolder + "/" + newName));
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

    private void initTableView() {
        TableColumn<FileItem, String> columnFileName = new TableColumn<>("Имя");
        columnFileName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<FileItem, String> columnExtension = new TableColumn<>("Тип");
        columnExtension.setCellValueFactory(new PropertyValueFactory<>("extension"));

        TableColumn<FileItem, Long> columnSize = new TableColumn<>("Размер");
        columnSize.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<FileItem, Date> columnDate = new TableColumn<>("Дата");
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<FileItem, Boolean> columnExist = new TableColumn<>("Скачан");
        columnExist.setCellValueFactory(new PropertyValueFactory<>("exist"));
    }

    private void updateTreeView(DirectoryCommandData directoryTree) {
        Platform.runLater(() -> {
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
                    TreeItem<FileTreeItem> parent = findItemByPath(rootNode, parentName);
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

    void onCommandSuccess(Command networkCommand) {
        Command command = (Command) networkCommand;
        switch (command.getType()) {
            case SET_DIR: {
                updateTreeView((DirectoryCommandData) command.getData());
                break;
            }
            case CREATE_FOLDER: {
                Network.getNetwork().getDirectory();
                break;
            }
            case RENAME_FOLDER: {
                Network.getNetwork().getDirectory();
                break;
            }
            case DELETE_FOLDER: {
                Network.getNetwork().getDirectory();
                break;
            }
        }

    }

}
