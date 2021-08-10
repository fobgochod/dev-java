package com.fobgochod.system.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.先假设一个需求，写一个RPC
 * 2.来回通信，连接数量，拆包？
 * 3.动态代理呀，序列化，协议封装
 * 4.连接池
 * 5.就像调用本地方法一样去调用远程的方法，面向java中就是所谓的 面向interface开发
 * <p>
 * <p>
 * 上节课，基本写了一个能发送
 * 小问题，当并发通过一个连接发送后，服务端解析ByteBuf 转 对象的过程出错
 *
 * @author Xiao
 * @date 2021/8/8 0:19
 */
public class MyRPC {

    // 模拟Consumer端
    @Test
    public void startClient() throws Exception {

        new Thread(this::startServer).start();

        System.out.println("server started...");

        CountDownLatch latch = new CountDownLatch(Config.TEST_THREAD_COUNT);
        AtomicInteger num = new AtomicInteger(0);
        int clientSize = Config.TEST_THREAD_COUNT;
        Thread[] threads = new Thread[clientSize];
        for (int i = 0; i < clientSize; i++) {
            threads[i] = new Thread(() -> {
                // 动态代理实现
                Car car = getProxy(Car.class);

                Integer requestArg = num.incrementAndGet();
                String responseRes = car.doCar(requestArg);

                latch.countDown();
                System.out.printf("client %-10s request args: %-3s response result: %-2s\n", Thread.currentThread().getName(), requestArg, responseRes);
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        latch.await();
    }

    @Test
    public void startServer() {
        CarService car = new CarService();
        FlyService fly = new FlyService();
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.register(Car.class.getName(), car);
        dispatcher.register(Fly.class.getName(), fly);

        NioEventLoopGroup boss = new NioEventLoopGroup(Config.SERVER_THREAD_COUNT);
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
                        p.addLast(new ServerRequestHandler(dispatcher));
                    }
                }).bind(new InetSocketAddress("localhost", 9090));
        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getProxy(Class<T> intf) {
        //实现各个版本的动态代理。。。。
        ClassLoader loader = intf.getClassLoader();
        Class<?>[] methodInfo = {intf};


        return (T) Proxy.newProxyInstance(loader, methodInfo, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //如何设计我们的consumer对于provider的调用过程

                //1，调用 服务，方法，参数  ==> 封装成 message  [content]
                String name = intf.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();

                RpcContent content = new RpcContent();
                content.setName(name);
                content.setArgs(args);
                content.setMethodName(methodName);
                content.setParameterTypes(parameterTypes);

                // 序列化，content转字节数组
                byte[] msgBody = ObjectUtils.serialize(content);

                // 2. requestId+message, 本地要缓存
                // 协议：【header<>】【msgBody】
                RpcHeader header = RpcHeader.createHeader(msgBody);
                // 解决数据decode问题
                // TODO：Server: dispatcher Executor
                byte[] msgHeader = ObjectUtils.serialize(header);

                //3，连接池：：取得连接
                ClientFactory factory = ClientFactory.getFactory();
                //获取连接过程中： 开始-创建，过程-直接取
                NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("localhost", 9090));

                //4，发送--> 走IO  out -->走Netty（event 驱动）
                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);

                long id = header.getRequestId();
                CompletableFuture<String> res = new CompletableFuture<>();
                ClientResponseMappingCallback.addCallBack(id, res);
                byteBuf.writeBytes(msgHeader);
                byteBuf.writeBytes(msgBody);
                ChannelFuture channelFuture = clientChannel.writeAndFlush(byteBuf);
                channelFuture.sync();  //io是双向的，你看似有个sync，她仅代表out

                //5，？，如果从IO ，未来回来了，怎么将代码执行到这里
                //（睡眠/回调，如何让线程停下来？你还能让他继续。。。）
                return res.get(); //阻塞的
            }
        });
    }
}

class Config {
    public static final int HEADER_LENGTH = 112;
    public static final int CLIENT_COUNT = 1;
    public static final int CLIENT_THREAD_COUNT = 1;
    public static final int SERVER_THREAD_COUNT = 10;
    public static final int TEST_THREAD_COUNT = 50;
}

class ObjectUtils {

    static ByteArrayOutputStream out = new ByteArrayOutputStream();

    /**
     * 序列化
     *
     * @param msg 具体对象
     */
    public synchronized static byte[] serialize(Object msg) {
        out.reset();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(msg);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化
     *
     * @param bytes     字节数组
     * @param valueType 目标对象类型
     */
    public synchronized static <T> T deserialize(byte[] bytes, Class<T> valueType) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(in);
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class ClientPool {
    NioSocketChannel[] clients;
    Object[] lock;

    ClientPool(int size) {
        // init  连接都是空的
        clients = new NioSocketChannel[size];
        // 锁是可以初始化的
        lock = new Object[size];
        for (int i = 0; i < size; i++) {
            lock[i] = new Object();
        }
    }
}

/**
 * 源于 spark 源码
 */
class ClientFactory {

    NioEventLoopGroup clientWorker;
    Random rand = new Random();

    private ClientFactory() {
    }

    private static final ClientFactory factory;

    static {
        factory = new ClientFactory();
    }

    public static ClientFactory getFactory() {
        return factory;
    }

    // 一个consumer 可以连接很多的provider，每一个provider都有自己的pool  K,V
    ConcurrentHashMap<InetSocketAddress, ClientPool> outboxs = new ConcurrentHashMap<>();

    public synchronized NioSocketChannel getClient(InetSocketAddress address) {

        ClientPool clientPool = outboxs.get(address);
        if (clientPool == null) {
            outboxs.putIfAbsent(address, new ClientPool(Config.CLIENT_COUNT));
            clientPool = outboxs.get(address);
        }

        int i = rand.nextInt(Config.CLIENT_COUNT);

        if (clientPool.clients[i] != null && clientPool.clients[i].isActive()) {
            return clientPool.clients[i];
        }

        synchronized (clientPool.lock[i]) {
            return clientPool.clients[i] = create(address);
        }

    }

    private NioSocketChannel create(InetSocketAddress address) {

        //基于 netty 的客户端创建方式
        clientWorker = new NioEventLoopGroup(Config.CLIENT_THREAD_COUNT);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(clientWorker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ServerDecoderHandler());
                        p.addLast(new ClientResponseHandler());  //解决给谁的？？  requestID..
                    }
                }).connect(address);
        try {
            NioSocketChannel client = (NioSocketChannel) connect.sync().channel();
            return client;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/**
 * 通过客户端请求头附带的requestId来匹配服务端的响应
 */
class ClientResponseMappingCallback {
    static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void addCallBack(long requestId, CompletableFuture cb) {
        mapping.putIfAbsent(requestId, cb);
    }

    public static void runCallBack(RpcEntity msg) {
        CompletableFuture cf = mapping.get(msg.header.getRequestId());
        cf.complete(msg.getContent().getResData());
        removeCallback(msg.header.getRequestId());
    }

    private static void removeCallback(long requestId) {
        mapping.remove(requestId);
    }
}

/**
 * Consumer
 * 客户端处理响应的结果
 */
class ClientResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcEntity rpcEntity = (RpcEntity) msg;

        //曾经我们没考虑返回的事情
        ClientResponseMappingCallback.runCallBack(rpcEntity);
    }
}

/**
 * 父类里一定有channelRead{  前老的拼buf  decode(); 剩余留存 ;对out遍历 } -> bytebuf
 * 因为你偷懒，自己能不能实现！
 */
class ServerDecoderHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        while (buf.readableBytes() >= Config.HEADER_LENGTH) {
            byte[] bytes = new byte[Config.HEADER_LENGTH];
            // getBytes：从哪里读取，读多少，但是readerIndex不变
            buf.getBytes(buf.readerIndex(), bytes);
            RpcHeader header = ObjectUtils.deserialize(bytes, RpcHeader.class);

            // decode在2个方向都使用
            // 通信的协议
            if (buf.readableBytes() >= (Config.HEADER_LENGTH + header.getContentLength())) {
                // readBytes：移动指针到body开始的位置
                buf.readBytes(Config.HEADER_LENGTH);

                byte[] data = new byte[header.getContentLength()];
                buf.readBytes(data);
                RpcContent content = ObjectUtils.deserialize(data, RpcContent.class);

                if (header.getProtocol() == 0x14141414) {
                    // 客户端发送到服务端
                    out.add(new RpcEntity(header, content));
                } else if (header.getProtocol() == 0x14141424) {
                    // 服务端发送到客户端
                    out.add(new RpcEntity(header, content));
                }
            } else {
                break;
            }
        }
    }
}

class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    private final Dispatcher dispatcher;

    public ServerRequestHandler(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    //provider:
    //思考下解决方法？
    //
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        RpcEntity rpcEntity = (RpcEntity) msg;

        //如果假设处理完了，要给客户端返回了~！！！
        //你需要注意哪些环节~！！！！！！！！

        //ByteBuf
        //因为是个RPC吗，你得有requestId！！！！
        //在client那一侧也要解决解码问题

        //关注rpc通信协议  来的时候flag 0x14141414

        //有新的header+content
        // String ioThreadName = Thread.currentThread().getName();

        //1,直接在当前方法 处理IO和业务和返回

        //3，自己创建线程池
        //2,使用netty自己的EventLoop来处理业务及返回

        ctx.executor().execute(new Runnable() {
            // ctx.executor().parent().next().execute(new Runnable() {

            @Override
            public void run() {

                String serviceName = rpcEntity.content.getName();
                String method = rpcEntity.content.getMethodName();
                Object service = dispatcher.get(serviceName);

                Class<?> clazz = service.getClass();
                Object result = null;
                try {
                    Method m = clazz.getMethod(method, rpcEntity.content.parameterTypes);
                    result = m.invoke(service, rpcEntity.content.getArgs());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // String execThreadName = Thread.currentThread().getName();
                // System.out.printf("io thread: %s exec thread: %s from args: %s\n", ioThreadName, execThreadName, httpEntity.content.getArgs()[0]);

                RpcContent content = new RpcContent();
                content.setResData((String) result);
                byte[] contentByte = ObjectUtils.serialize(content);

                RpcHeader resHeader = new RpcHeader();
                resHeader.setRequestId(rpcEntity.header.getRequestId());
                resHeader.setProtocol(0x14141424);
                resHeader.setContentLength(contentByte.length);
                byte[] headerByte = ObjectUtils.serialize(resHeader);
                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerByte.length + contentByte.length);

                byteBuf.writeBytes(headerByte);
                byteBuf.writeBytes(contentByte);
                ctx.writeAndFlush(byteBuf);
            }
        });
    }
}

/**
 * 通信上的协议
 * 1.protocol: 32bit可以设置很多信息
 * 客户端发送到服务端: 0x14141414  14->0001 0100
 * 服务端发送到客户端: 0x14141424
 * 2.contentLength: 数据长度
 * 3.UUID: requestId
 */
class RpcHeader implements Serializable {

    int protocol;
    int contentLength;
    long requestId;

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public static RpcHeader createHeader(byte[] body) {
        RpcHeader header = new RpcHeader();
        // 0x14 0001 0100
        int protocol = 0x14141414;
        int contentLength = body.length;
        long requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        header.setProtocol(protocol);
        header.setContentLength(contentLength);
        header.setRequestId(requestId);
        return header;
    }
}

class RpcContent implements Serializable {

    String name;
    String methodName;
    Class<?>[] parameterTypes;
    Object[] args;
    String resData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getResData() {
        return resData;
    }

    public void setResData(String resData) {
        this.resData = resData;
    }
}

class RpcEntity {

    RpcHeader header;
    RpcContent content;

    public RpcHeader getHeader() {
        return header;
    }

    public void setHeader(RpcHeader header) {
        this.header = header;
    }

    public RpcContent getContent() {
        return content;
    }

    public void setContent(RpcContent content) {
        this.content = content;
    }

    public RpcEntity(RpcHeader header, RpcContent content) {
        this.header = header;
        this.content = content;
    }
}


/**
 * 服务注册和发现
 */
class Dispatcher {

    public static ConcurrentHashMap<String, Object> invokeMap = new ConcurrentHashMap<>();

    public void register(String key, Object obj) {
        invokeMap.put(key, obj);
    }

    public Object get(String key) {
        return invokeMap.get(key);
    }
}

interface Car {
    String doCar(Integer msg);
}

interface Fly {
    void doFly(String msg);
}

class CarService implements Car {

    @Override
    public String doCar(Integer msg) {
        // System.out.println("do car invoke, client arg:" + msg);
        return String.valueOf(msg + 100);
    }
}

class FlyService implements Fly {

    @Override
    public void doFly(String msg) {
        // System.out.println("do fly invoke, client arg:" + msg);
    }
}
