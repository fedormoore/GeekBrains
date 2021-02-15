package ru.moore.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import ru.moore.Command;
import ru.moore.utils.FilePart;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.moore.controller.ActiveController.BASE_PATH;

public class Network {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8190;

    private SocketChannel socketChannel;

    private final String host;
    private final int port;

    private CommandCallback commandCallback;

    private static Network network;

    public Network() {
        this(SERVER_ADDRESS, SERVER_PORT);
    }

    public Network(String host, int port) {
        this.host = host;
        this.port = port;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                NioEventLoopGroup clientGroup = new NioEventLoopGroup();
                try {
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(clientGroup)
                            .channel(NioSocketChannel.class)
                            .remoteAddress(new InetSocketAddress(host, port))
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel channel) throws Exception {
                                    socketChannel = channel;
                                    System.out.println(555);
                                    socketChannel.pipeline().addLast(
                                            new ObjectEncoder(),
                                            new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                            new ClientHandler(commandCallback));
                                }
                            });
                    ChannelFuture future = bootstrap.connect().sync();
                    future.channel().closeFuture().sync();
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                } finally {
                    clientGroup.shutdownGracefully();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void sendRegCommand(String username, String login, String password) {
        Command regCommand = Command.regCommand(username, login, password);
        sendMessage(regCommand);
    }

    public void sendAuthCommand(String login, String password) {
        Command authCommand = Command.authCommand(login, password);
        sendMessage(authCommand);
    }

    public void getDirectory() {
        Command getDirectory = Command.getDirectoryCommand();
        sendMessage(getDirectory);
    }

    public void createFolder(String path) {
        Command createFolder = Command.createFolderCommand(path);
        sendMessage(createFolder);
    }

    public void renameFolder(String oldFolderName, String newFolderName) {
        Command renameFolder = Command.renameFolderCommand(oldFolderName, newFolderName);
        sendMessage(renameFolder);
    }

    public void deleteFolder(String path) {
        Command deleteFolder = Command.deleteFolderCommand(path);
        sendMessage(deleteFolder);
    }

    public void getFiles(String currentFolder) {
        Command files = Command.getFilesCommand(currentFolder);
        sendMessage(files);
    }

    public void downloadFile(String currentFile) {
        Command files = Command.getDownloadFileCommand(currentFile);
        sendMessage(files);
    }

    public void deleteFile(String currentFile) {
        Command files = Command.deleteFileCommand(currentFile);
        sendMessage(files);
    }

    public void renameFile(String currentFolder, String oldFileName, String newFileName) {
        Command renameFolder = Command.renameFilesCommand(currentFolder, oldFileName, newFileName);
        sendMessage(renameFolder);
        Path filePath = Paths.get(BASE_PATH + "/" + currentFolder+"/"+oldFileName);
        filePath.normalize();
        if (Files.exists(filePath)) {
            try {
                Files.move(filePath, filePath.getParent().resolve(newFileName));
            } catch (IOException e) {

            }
        }
    }

    public void uploadFile(String absolutePath, String currentFolder) {
        Path filePath = Paths.get(absolutePath);
        String fileName = Paths.get(currentFolder, filePath.getFileName().toString()).toString();

        if (Files.exists(filePath)) {
            byte[] buffer = new byte[FilePart.partSize];

            int read;

            try (InputStream inputStream = new FileInputStream(filePath.toAbsolutePath().toString())) {
                float size = inputStream.available();
                int iterator = 1;
                Command response;
                while ((read = inputStream.read(buffer)) != -1) {
                    float progress = ((float) (buffer.length * iterator)) / size;
                    response = Command.filePartTransferCommand(fileName, buffer, read, false, progress, iterator);
                    sendMessage(response);
                    iterator++;
                }
                response = Command.filePartTransferCommand(fileName, buffer, read, true, 0, iterator);
                sendMessage(response);
            } catch (IOException e) {

            }
        }
    }

    public Network onCommand(CommandCallback commandCallback) {
        this.commandCallback = commandCallback;
        if (socketChannel != null) {
            socketChannel.pipeline().removeLast();
            socketChannel.pipeline().addLast(new ClientHandler(commandCallback));
        }
        return this;
    }

    public static Network getNetwork() {
        if (network == null) {
            network = new Network();
        }
        return network;
    }

    public void close() {
        socketChannel.close();
    }

    public void sendMessage(Object o) {
        socketChannel.writeAndFlush(o);
    }

}
