package com.fobgochod.system.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * EPOLL 注释
 * <p>
 * -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.EPollSelectorProvider
 * <p>
 * 使用strace命令追踪
 * javac SocketMultiplexingEpoll.java && strace -ff -o poll java SocketMultiplexingEpoll
 *
 * @author Xiao
 * @date 2021/8/7 13:00
 */
public class SocketMultiplexingEpoll {

    private ServerSocketChannel server = null;
    int port = 9090;
    private Selector selector = null;


    public static void main(String[] args) {
        SocketMultiplexingEpoll service = new SocketMultiplexingEpoll();
        service.start();
    }

    public void initServer() {
        try {
            // socket(AF_INET6, SOCK_STREAM, IPPROTO_IP) = 8
            server = ServerSocketChannel.open();

            // fcntl(8, F_SETFL, O_RDWR|O_NONBLOCK)    = 0
            server.configureBlocking(false);

            // bind(8, {sa_family=AF_INET6, sin6_port=htons(9090), sin6_flowinfo=htonl(0), inet_pton(AF_INET6, "::", &sin6_addr), sin6_scope_id=0}, 28) = 0
            // listen(8, 50)
            server.bind(new InetSocketAddress(port));

            // epoll_create(256)                       = 11
            selector = Selector.open();

            // 懒加载：其实在实际调用selector.select()的时候才会触发epoll_ctl的调用
            // epoll_ctl(11, EPOLL_CTL_ADD, 8, {EPOLLIN, {u32=8, u64=139835545223176}}) = 0
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。。。");
        try {
            while (true) {
                Set<SelectionKey> keys = selector.keys();
                System.out.println(keys.size() + "   size");

                /**
                 * 第一次循环：
                 *     epoll_ctl(11, EPOLL_CTL_ADD, 8, {EPOLLIN, {u32=8, u64=139835545223176}}) = 0
                 *     epoll_wait(11, [{EPOLLIN, {u32=8, u64=139835545223176}}], 8192, -1) = 1
                 * 第二次循环：
                 *     epoll_ctl(11, EPOLL_CTL_ADD, 12, {EPOLLIN, {u32=12, u64=139835545223180}}) = 0
                 *     epoll_wait(11, [{EPOLLIN, {u32=12, u64=139835545223180}}], 8192, -1) = 1
                 */
                while (selector.select() > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    Iterator<SelectionKey> iter = selectionKeys.iterator();

                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            // accept(8, {sa_family=AF_INET6, sin6_port=htons(44358), sin6_flowinfo=htonl(0), inet_pton(AF_INET6, "::1", &sin6_addr), sin6_scope_id=0}, [28]) = 12
            SocketChannel client = ssc.accept();
            // fcntl(12, F_SETFL, O_RDWR|O_NONBLOCK)   = 0
            client.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(8192);

            // 懒加载：其实在实际调用selector.select()的时候才会触发epoll_ctl的调用
            // epoll_ctl(11, EPOLL_CTL_ADD, 12, {EPOLLIN, {u32=12, u64=139835545223180}}) = 0
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = -1;
        try {
            while (true) {
                try {
                    read = client.read(buffer);
                } catch (Exception e) {
                    System.out.println(e.getMessage() + ": " + client);
                }
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
