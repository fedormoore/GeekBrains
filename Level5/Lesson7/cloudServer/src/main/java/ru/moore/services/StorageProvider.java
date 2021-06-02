package ru.moore.services;

import io.netty.util.internal.SystemPropertyUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.moore.commands.DirectoryCommandData;
import ru.moore.utils.FileItem;
import ru.moore.utils.FileTreeItem;
import ru.moore.utils.FilesList;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

public class StorageProvider {

    public static final Path BASE_PATH = Paths.get(SystemPropertyUtil.get("user.dir") + "/server/userDir");

    private static final Logger logger = LogManager.getLogger(StorageProvider.class.getName());

    public static void createRootPath(String userName) {
        Path rootPath = Path.of(BASE_PATH + "/" + userName);
        if (Files.notExists(rootPath)) {
            try {
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    public static DirectoryCommandData getUserDir(String username) {
        DirectoryCommandData directory = new DirectoryCommandData();
        Path userRoot = Path.of(BASE_PATH + "/" + username);
        try {
            Files.walkFileTree(userRoot,
                    new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            if (dir != userRoot) {
                                directory.add(new FileTreeItem(
                                        dir.getFileName().toString(),
                                        userRoot.relativize(dir).toString()));
                            }
                            return super.preVisitDirectory(dir, attrs);
                        }
                    }
            );
        } catch (IOException e) {
            logger.error(e);
        }
        return directory;
    }

    static Path getDirectoryPath(String username, String filePath) {
        Path userRoot = Path.of(BASE_PATH + "/" + username);
        return userRoot.resolve(filePath);
    }

    public static void deleteDir(String nameFolder, String username) {
        Path userRoot = Path.of(BASE_PATH + "/" + username + "/" + nameFolder);
        try {
            Files.walkFileTree(userRoot,
                    new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            if (dir != userRoot) {
                                Files.delete(Path.of(userRoot + "/" + userRoot.relativize(dir).toString()));
                            }
                            return super.preVisitDirectory(dir, attrs);
                        }
                    }
            );
            Files.delete(userRoot);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static FilesList getFilesUser(String username, String nameFolder) {
        FilesList list = new FilesList();

        Path directory = Path.of(BASE_PATH + "/" + username + "/" + nameFolder);

        directory.normalize();
        try {
            Files.walkFileTree(directory, Set.of(), 1,
                    new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            if (!Files.isDirectory(file)) {
                                list.add(FileItem.fromPath(file));
                            }
                            return super.visitFile(file, attrs);
                        }
                    }
            );
        } catch (IOException e) {
            logger.error(e);
        }

        return list;
    }

}
