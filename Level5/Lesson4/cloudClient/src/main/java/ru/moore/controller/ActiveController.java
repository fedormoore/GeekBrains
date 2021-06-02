package ru.moore.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.moore.Command;
import ru.moore.commands.DirectoryCommandData;
import ru.moore.commands.FileItem;
import ru.moore.commands.FileTreeItem;
import ru.moore.network.Network;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ActiveController implements Initializable {

    private final TreeItem<FileTreeItem> rootNode = new TreeItem<>(new FileTreeItem("Сервер", ""));

    public TreeView<FileTreeItem> treeView;

    private String currentFolder = "./";
    private String currentFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootNode.setExpanded(true);
        Network.getNetwork().onCommand(this::onCommandSuccess);
        Network.getNetwork().getDirectory();
        treeView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldItem, newItem)
                -> {
            currentFolder = newItem.getValue().getPath();
            currentFile = null;
        });
        initTableView();
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
            case GET_DIR: {
                updateTreeView((DirectoryCommandData) command.getData());
            }
        }

    }

}
