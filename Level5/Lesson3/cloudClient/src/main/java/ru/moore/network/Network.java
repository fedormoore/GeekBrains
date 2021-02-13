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

import java.net.InetSocketAddress;

public class Network {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8190;

    private SocketChannel socketChannel;

    private final String host;
    private final int port;

    private CommandCallback commandCallback;

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

    public Network onCommand(CommandCallback commandCallback) {
        this.commandCallback = commandCallback;
        if (socketChannel != null) {
            socketChannel.pipeline().removeLast();
            socketChannel.pipeline().addLast(new ClientHandler(commandCallback));
        }
        return this;
    }


    public void close() {
        socketChannel.close();
    }

    public void sendMessage(Object o) {
        socketChannel.writeAndFlush(o);
    }
}
