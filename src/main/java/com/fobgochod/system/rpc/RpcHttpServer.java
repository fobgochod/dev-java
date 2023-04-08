package com.fobgochod.system.rpc;

import com.fobgochod.system.rpc.dispatcher.Dispatcher;
import com.fobgochod.system.rpc.handler.ServerServletHandler;
import com.fobgochod.system.rpc.handler.ServerNettyHttpHandler;
import com.fobgochod.system.rpc.util.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

/**
 * RPC服务端
 *
 * @author fobgochod
 * @date 2021/8/9 15:57
 */
public class RpcHttpServer {

    /**
     * 2.小火车，传输协议用的就是http了  <- 你可以自己学，字节节码byte[]
     * 基于netty提供了一套编解码
     */
    @Test
    public void nettyServer() {

        Dispatcher.registerService();

        NioEventLoopGroup boss = new NioEventLoopGroup(Constants.SERVER_THREAD_COUNT);
        NioEventLoopGroup worker = boss;

        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind = sbs.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server accept client port: " + ch.remoteAddress().getPort());
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(1024 * 512))
                                .addLast(new ServerNettyHttpHandler());
                    }
                })
                .bind(new InetSocketAddress("localhost", 9090));

        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 3. tomcat jetty【servlet】
     */
    @Test
    public void jettyServer() {

        Dispatcher.registerService();

        Server server = new Server(new InetSocketAddress("localhost", 9090));
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        server.setHandler(handler);

        // web.xml
        handler.addServlet(ServerServletHandler.class, "/*");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
