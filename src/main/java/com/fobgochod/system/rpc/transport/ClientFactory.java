package com.fobgochod.system.rpc.transport;


import com.fobgochod.system.rpc.handler.ClientCustomHandler;
import com.fobgochod.system.rpc.handler.ClientNettyHandler;
import com.fobgochod.system.rpc.handler.ClientNettyMultiplexingHandler;
import com.fobgochod.system.rpc.handler.ServerDecoderHandler;
import com.fobgochod.system.rpc.protocol.RpcContent;
import com.fobgochod.system.rpc.protocol.RpcHeader;
import com.fobgochod.system.rpc.protocol.RpcType;
import com.fobgochod.system.rpc.util.Constants;
import com.fobgochod.system.rpc.util.ObjectUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClientFactory.java
 *
 * @author fobgochod
 * @date 2021/8/9 16:18
 */
public class ClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(ClientFactory.class);

    NioEventLoopGroup clientWorker;
    private final Random rand = new Random();

    private ClientFactory() {
    }

    private static final ClientFactory factory;

    static {
        factory = new ClientFactory();
    }

    public static ClientFactory getFactory() {
        return factory;
    }

    /**
     * content  就是货物  现在可以用自定义的rpc传输协议（有状态），也可以用http协议作为载体传输
     * 我们先手工用了http协议作为载体，那这样是不是代表我们未来可以让provider是一个tomcat  jetty 基于http协议的一个容器
     * 有无状态来自于你使用的什么协议，那么http协议肯定是无状态，每请求对应一个连接
     * dubbo 是一个rpc框架  netty 是一个io框架
     * dubbo中传输协议上，可以是自定义的rpc传输协议，http协议
     *
     * @param rpcType
     * @param content 客户端发送内容 数据包
     */
    public static CompletableFuture<Object> transport(RpcType rpcType, RpcContent content) {

        CompletableFuture<Object> future = new CompletableFuture<>();
        if (rpcType == RpcType.CUSTOM) {
            // 0. 自定义RPC
            customRpc(content, future);
        } else if (rpcType == RpcType.HTTP_ON_TAP) {
            // 使用http协议为载体
            // 1. 用URL 现成的工具（包含了http的编解码，发送，socket，连接）
            onTapHttp(content, future);
        } else if (rpcType == RpcType.HTTP_NETTY) {
            // 2. 自己操心：on netty  （io 框架）+ 已经提供的http相关的编解码
            nettyHttp(content, future);
        } else if (rpcType == RpcType.HTTP_NETTY_MULTIPLEXING) {
            // 3. netty提供的http协议解析，多次请求复用一个链接
            nettyHttpMultiplexing(content, future);
        }
        return future;
    }

    private static void customRpc(RpcContent content, CompletableFuture<Object> future) {
        byte[] msgBody = ObjectUtils.serialize(content);
        RpcHeader header = RpcHeader.requestHeader(msgBody);
        byte[] msgHeader = ObjectUtils.serialize(header);
        if (msgHeader.length != Constants.HEADER_LENGTH) {
            logger.error("Header序列化结果[{}]与指定值[{}]不一致", msgHeader.length, Constants.HEADER_LENGTH);
        }

        ClientRequestMapping.addCallBack(header.getRequestId(), future);

        NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("localhost", 9090), RpcType.CUSTOM);
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
        byteBuf.writeBytes(msgHeader);
        byteBuf.writeBytes(msgBody);

        clientChannel.writeAndFlush(byteBuf);
    }

    private static void nettyHttpMultiplexing(RpcContent content, CompletableFuture<Object> future) {

        NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("localhost", 9090), RpcType.HTTP_NETTY);

        //2，发送
        byte[] data = ObjectUtils.serialize(content);
        long requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        ClientRequestMapping.addCallBack(requestId, future);

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0,
                HttpMethod.POST, "/",
                Unpooled.copiedBuffer(data)
        );
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, data.length);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(Constants.HTTP_REQUEST_ID, requestId);

        //作为client 向server端发送：http  request
        clientChannel.writeAndFlush(request);
    }

    private static void nettyHttp(RpcContent content, CompletableFuture<Object> future) {
        //在这个执行之前  我们的server端 provider端已经开发完了，已经是on netty的http server了
        //现在做的事consumer端的代码修改，改成 on netty的http client
        //刚才一切都顺利，关注未来的问题。。。。。。

        //每个请求对应一个连接
        //1，通过netty建立io 建立连接
        //TODO  改成 多个http的request 复用一个 netty client，而且 并发发送请求
        NioEventLoopGroup group = new NioEventLoopGroup(Constants.CLIENT_THREAD_COUNT); //定义到外面
        Bootstrap bs = new Bootstrap();
        Bootstrap client = bs.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new HttpClientCodec())
                                .addLast(new HttpObjectAggregator(1024 * 512))
                                .addLast(new ClientNettyHandler(future));
                    }
                });
        //未来连接后，收到数据的处理handler
        try {
            ChannelFuture syncFuture = client.connect("localhost", 9090).sync();
            //2，发送
            Channel clientChannel = syncFuture.channel();
            byte[] data = ObjectUtils.serialize(content);
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0,
                    HttpMethod.POST, "/",
                    Unpooled.copiedBuffer(data)
            );
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, data.length);

            //作为client 向server端发送：http  request
            clientChannel.writeAndFlush(request).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 无状态、阻塞
     * 这种方式是每请求占用一个连接的方式，因为使用的是http协议
     *
     * @param content
     * @param future
     */
    private static void onTapHttp(RpcContent content, CompletableFuture<Object> future) {

        Object obj = null;
        try {
            URL url = new URL("http://localhost:9090/");

            HttpURLConnection hc = (HttpURLConnection) url.openConnection();

            //post
            hc.setRequestMethod("POST");
            hc.setDoOutput(true);
            hc.setDoInput(true);

            OutputStream out = hc.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            // 这里真的发送了嘛？
            oos.writeObject(content);

            if (hc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = hc.getInputStream();
                ObjectInputStream oin = new ObjectInputStream(in);
                RpcContent rpcContent = (RpcContent) oin.readObject();
                obj = rpcContent.getResData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        future.complete(obj);
    }


    /**
     * 一个consumer 可以连接很多的provider，每一个provider都有自己的pool  K,V
     */
    final ConcurrentHashMap<InetSocketAddress, ClientPool> clientMap = new ConcurrentHashMap<>();

    public NioSocketChannel getClient(InetSocketAddress address, RpcType rpcType) {
        //TODO 在并发情况下一定要谨慎
        ClientPool clientPool = clientMap.get(address);
        if (clientPool == null) {
            synchronized (clientMap) {
                clientPool = clientMap.get(address);
                if (clientPool == null) {
                    clientMap.putIfAbsent(address, new ClientPool(Constants.CLIENT_COUNT));
                    clientPool = clientMap.get(address);
                }
            }
        }

        int i = rand.nextInt(Constants.CLIENT_COUNT);

        if (clientPool.clients[i] != null && clientPool.clients[i].isActive()) {
            return clientPool.clients[i];
        } else {
            synchronized (clientPool.lock[i]) {
                if (clientPool.clients[i] == null || !clientPool.clients[i].isActive()) {
                    if (rpcType == RpcType.HTTP_NETTY) {
                        clientPool.clients[i] = createNettyClient(address);
                    } else {
                        clientPool.clients[i] = createClient(address);
                    }
                }
            }
            return clientPool.clients[i];
        }
    }

    private NioSocketChannel createClient(InetSocketAddress address) {

        //基于 netty 的客户端创建方式
        clientWorker = new NioEventLoopGroup(Constants.CLIENT_THREAD_COUNT);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(clientWorker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ServerDecoderHandler());
                        p.addLast(new ClientCustomHandler());  //解决给谁的？？  requestID..
                    }
                }).connect(address);
        try {
            return (NioSocketChannel) connect.sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private NioSocketChannel createNettyClient(InetSocketAddress address) {

        NioEventLoopGroup group = new NioEventLoopGroup(Constants.CLIENT_THREAD_COUNT);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new HttpClientCodec());
                        p.addLast(new HttpObjectAggregator(1024 * 512));
                        p.addLast(new ClientNettyMultiplexingHandler());
                    }
                }).connect(address);
        try {
            return (NioSocketChannel) connect.sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

