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
 * POLL 注释
 * <p>
 * -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.PollSelectorProvider
 * <p>
 * 使用strace命令追踪
 * javac SocketMultiplexingPoll.java && strace -ff -o poll java -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.PollSelectorProvider SocketMultiplexingPoll
 *
 * @author Xiao
 * @date 2021/8/7 13:00
 */
public class SocketMultiplexingPoll {

    int port = 9090;
    private ServerSocketChannel server = null;
    private Selector selector = null;


    public static void main(String[] args) {
        SocketMultiplexingPoll service = new SocketMultiplexingPoll();
        service.start();
    }

    public void initServer() {
        try {
            // socket(AF_INET6, SOCK_STREAM, IPPROTO_IP) = 6
            server = ServerSocketChannel.open();

            // fcntl(6, F_SETFL, O_RDWR|O_NONBLOCK)    = 0
            server.configureBlocking(false);

            // bind(6, {sa_family=AF_INET6, sin6_port=htons(9090), sin6_flowinfo=htonl(0), inet_pton(AF_INET6, "::", &sin6_addr), sin6_scope_id=0}, 28) = 0
            // listen(6, 50)
            server.bind(new InetSocketAddress(port));

            selector = Selector.open();

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
                // 第一次循环：poll([{fd=7, events=POLLIN}, {fd=6, events=POLLIN}], 2, -1) = 1 ([{fd=6, revents=POLLIN}])
                // 第二次循环：poll([{fd=7, events=POLLIN}, {fd=6, events=POLLIN}, {fd=9, events=POLLIN}], 3, -1) = 1 ([{fd=9, revents=POLLIN}])
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
            // accept(6, {sa_family=AF_INET6, sin6_port=htons(44266), sin6_flowinfo=htonl(0), inet_pton(AF_INET6, "::1", &sin6_addr), sin6_scope_id=0}, [28]) = 9
            SocketChannel client = ssc.accept();
            // fcntl(9, F_SETFL, O_RDWR|O_NONBLOCK)    = 0
            client.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(8192);

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
