package com.fobgochod.system.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * MyNetty
 *
 * @author fobgochod
 * @date 2021/8/4 20:22
 */
public class MyNetty {

    /*
    今天主要是netty的初级使用，如果对初级知识过敏的小伙伴可以
    先学点高级的 -。-
    非常初级。。。。
     */

    /*
    目的：前边 NIO 逻辑
    恶心的版本---依托着前面的思维逻辑
    channel  ByteBuffer  Selector
    ByteBuffer   ByteBuf【pool】
     */


    @Test
    public void myByteBuf() {

        // ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(8, 20);
        // pool
        // ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        print(buf);

        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        // buf.writeBytes(new byte[]{1, 2, 3, 4});
        // print(buf);
    }

    public static void print(ByteBuf buf) {
        System.out.println("buf.isReadable()    :" + buf.isReadable());
        System.out.println("buf.readerIndex()   :" + buf.readerIndex());
        System.out.println("buf.readableBytes() :" + buf.readableBytes());
        System.out.println("buf.isWritable()    :" + buf.isWritable());
        System.out.println("buf.writerIndex()   :" + buf.writerIndex());
        System.out.println("buf.writableBytes() :" + buf.writableBytes());
        System.out.println("buf.capacity()      :" + buf.capacity());
        System.out.println("buf.maxCapacity()   :" + buf.maxCapacity());
        System.out.println("buf.isDirect()      :" + buf.isDirect());
        System.out.println("--------------");
    }

    /**
     * 客户端
     * 连接别人
     * 1.主动发送数据
     * 2.别人什么时候给我发？  event  selector
     */
    @Test
    public void loopExecutor() throws Exception {
        // group 线程池
        NioEventLoopGroup selector = new NioEventLoopGroup(2);
        selector.execute(() -> {
            try {
                for (; ; ) {
                    System.out.println("hello world 001");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        selector.execute(() -> {
            try {
                for (; ; ) {
                    System.out.println("hello world 002");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.in.read();
    }


    @Test
    public void clientMode() throws Exception {

        NioEventLoopGroup thread = new NioEventLoopGroup(1);

        //客户端模式：
        NioSocketChannel client = new NioSocketChannel();

        // epoll_ctl(5,ADD,3)
        thread.register(client);

        //响应式：
        ChannelPipeline p = client.pipeline();
        p.addLast(new MyInHandler());

        //reactor 异步的特征
        ChannelFuture connect = client.connect(new InetSocketAddress("47.100.125.75", 9090));
        ChannelFuture sync = connect.sync();

        ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        //马老师的多线程
        sync.channel().closeFuture().sync();

        System.out.println("client over....");
    }

    @Test
    public void nettyClient() throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(group)
                .channel(NioSocketChannel.class)
                // .handler(new ChannelInit())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyInHandler());
                    }
                })
                .connect(new InetSocketAddress("47.100.125.75", 9090));

        Channel client = connect.sync().channel();

        ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        client.closeFuture().sync();
    }


    @Test
    public void serverMode() throws Exception {

        NioEventLoopGroup thread = new NioEventLoopGroup(1);
        NioServerSocketChannel server = new NioServerSocketChannel();

        thread.register(server);
        //指不定什么时候家里来人。。响应式
        ChannelPipeline pipeline = server.pipeline();

        // accept接收客户端，并且注册到selector
        // MyInHandler 需要被@ChannelHandler.Sharable注解
        // pipeline.addLast(new MyAcceptHandler(thread, new MyInHandler()));
        pipeline.addLast(new MyAcceptHandler(thread, new ChannelInit()));

        ChannelFuture bind = server.bind(new InetSocketAddress("0.0.0.0", 9090));

        bind.sync().channel().closeFuture().sync();
        System.out.println("server close...");
    }

    @Test
    public void nettyServer() throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap bs = new ServerBootstrap();
        ChannelFuture bind = bs.group(group, group)
                .channel(NioServerSocketChannel.class)
                // .childHandler(new ChannelInit())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MyInHandler());
                    }
                })
                .bind(new InetSocketAddress("0.0.0.0", 9090));

        bind.sync().channel().closeFuture().sync();
    }
}

class MyAcceptHandler extends ChannelInboundHandlerAdapter {

    private final EventLoopGroup selector;
    private final ChannelHandler handler;

    public MyAcceptHandler(EventLoopGroup thread, ChannelHandler handler) {
        this.selector = thread;
        this.handler = handler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server registered...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // listen socket  accept => client
        // socket R/W
        SocketChannel client = (SocketChannel) msg;  //accept  我怎么没调用额？
        // 2.响应式的handler
        ChannelPipeline pipeline = client.pipeline();
        // 1.client::pipeline[ChannelInit,]
        pipeline.addLast(handler);

        //1，注册
        selector.register(client);
    }
}

/**
 * 为啥要有一个initHandler，可以没有，但是MyInHandler就得设计成单例
 */
@ChannelHandler.Sharable
class ChannelInit extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        ChannelPipeline pipeline = client.pipeline();

        // 2.client::pipeline[ChannelInit,MyInHandler]
        pipeline.addLast(new MyInHandler());

        // 3.client::pipeline[MyInHandler]
        ctx.pipeline().remove(this);
    }
}


/**
 * 就是用户自己实现的，你能说让用户放弃属性的操作吗
 *
 * @ChannelHandler.Sharable 不应该被强压给coder
 */
// @ChannelHandler.Sharable
class MyInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client registered..." + ctx.channel().remoteAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        // CharSequence str = buf.readCharSequence(buf.readableBytes(), CharsetUtil.UTF_8);
        CharSequence str = buf.getCharSequence(0, buf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(str);
        ctx.writeAndFlush(buf);
    }
}
