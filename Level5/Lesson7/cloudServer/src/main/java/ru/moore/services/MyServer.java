package ru.moore.services;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.moore.dao.Db;
import ru.moore.repositories.UsersRepository;
import ru.moore.repositories.impl.UsersRepositoryImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MyServer{

    private static final Logger logger = LogManager.getLogger(MyServer.class.getName());

    public MyServer(int port, Db db, Connection connection) throws IOException {
        UsersRepository usersRepository = new UsersRepositoryImpl(connection);

        EventLoopGroup auth = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(auth, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new InHandler(usersRepository)
                            );
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException  e) {
            logger.error(e);
        } finally {
            auth.shutdownGracefully();
            worker.shutdownGracefully();
            try {
                db.disconnect();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }
}
