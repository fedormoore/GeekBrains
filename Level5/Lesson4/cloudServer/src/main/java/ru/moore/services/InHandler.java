package ru.moore.services;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.moore.Command;
import ru.moore.repositories.UsersRepository;

public class InHandler extends SimpleChannelInboundHandler<Object> {

    private CommandProcessor commandProcessor;

    public InHandler(UsersRepository usersRepository) {
        commandProcessor = new CommandProcessor(usersRepository);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        Command command =(Command) o;
        if (command == null) {
            return;
        }

        switch (command.getType()) {
//            case END:
//                return;
            case AUTH_USER: {
                commandProcessor.processAuthCommand(command, ctx);
                break;
            }
            case REG_USER: {
                commandProcessor.processRegCommand(command, ctx);
                break;
            }
            case GET_DIR: {
                commandProcessor.processGetDirectory(ctx);
                break;
            }

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

    }


}
