package com.fobgochod.system.rpc;

import com.fobgochod.system.rpc.dispatcher.Dispatcher;
import com.fobgochod.system.rpc.handler.ServerCustomHandler;
import com.fobgochod.system.rpc.handler.ServerDecoderHandler;
import com.fobgochod.system.rpc.util.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * RPC服务端
 * 自定义传输协议
 * 粘包拆包的问题，header+body
 *
 * @author Xiao
 * @date 2021/8/10 9:52
 */
public class MyRpcServer {

    /**
     * 1.自定义的RPC
     * 在自己定义协议的时候你关注过哪些问题：粘包拆包的问题，header+body
     */
    @Test
    public void rpcServer() {
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
                        p.addLast(new ServerDecoderHandler());
                        p.addLast(new ServerCustomHandler());
                    }
                })
                .bind(new InetSocketAddress("localhost", 9090));

        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
