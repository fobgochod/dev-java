package com.fobgochod.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class ChatClient {

    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);

    public void chatClient() {
        final ClientHandler clientHandler = new ClientHandler();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(ChatServer.HOST, ChatServer.PORT))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(clientHandler);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect().sync();
            Channel channel = channelFuture.channel();
            //客户端发送消息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String msg = scanner.nextLine();
                //通过客户端把输入内容发送到服务端
                channel.writeAndFlush(msg).sync();
                if ("quit".equals(msg)) {
                    channel.close().sync();
                    break;
                }
            }
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("client error:", e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    static class ClientHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            logger.info(msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            Channel channel = ctx.channel();
            String msg = cause.getMessage();
            logger.info("群聊[" + channel.remoteAddress() + "]异常: " + msg);
        }
    }
}
