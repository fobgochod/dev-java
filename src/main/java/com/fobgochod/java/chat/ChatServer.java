package com.fobgochod.java.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

public class ChatServer {

    static final String HOST = "localhost";
    static final int PORT = 8080;
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    @Test
    public void chatServer() {

        final ServerHandler serverHandler = new ServerHandler();
        // 主线程组，用于接受客户端的连接，但是不做任何处理，跟老板一样
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组，主线程组会把任务丢给它，让从线程组去做相应的处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(PORT)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(serverHandler);
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("server error:", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    static class ServerHandler extends ChannelInboundHandlerAdapter {

        private static ChannelGroup clientChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            clientChannels.add(channel);

            String sendMsg = "客户[" + channel.remoteAddress() + "]上线\n";
            logger.info(sendMsg);
            clientChannels.forEach(clientChannel -> {
                if (clientChannel != channel) {
                    clientChannel.writeAndFlush(sendMsg);
                } else {
                    clientChannel.writeAndFlush("欢迎您上线\n");
                }
            });
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            if (clientChannels.contains(channel)) {
                clientChannels.remove(channel);

                String sendMsg = "客户[" + channel.remoteAddress() + "]异常下线\n";
                logger.info(sendMsg);
                clientChannels.forEach(clientChannel -> clientChannel.writeAndFlush(sendMsg));
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Channel channel = ctx.channel();

            AtomicReference<String> sendMsg = new AtomicReference<>("客户[" + channel.remoteAddress() + "]消息: " + msg + "\n");
            if ("quit".equals(msg)) {
                clientChannels.remove(channel);
                channel.close();
                sendMsg.set("客户[" + channel.remoteAddress() + "]下线\n");
                logger.info(sendMsg.get());
            }
            clientChannels.forEach(clientChannel -> {
                if (clientChannel != channel) {
                    clientChannel.writeAndFlush(sendMsg.get());
                }
            });
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            Channel channel = ctx.channel();
            clientChannels.remove(channel);

            String msg = cause.getMessage();
            String sendMsg = "客户[" + channel.remoteAddress() + "]异常: " + msg + "\n";
            logger.info(sendMsg);
            clientChannels.forEach(clientChannel -> clientChannel.writeAndFlush(sendMsg));
        }
    }
}
