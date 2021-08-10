package com.fobgochod.system.io;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * NIO
 *
 * @author Xiao
 * @date 2021/8/6 14:15
 */
public class SocketNIO {

    public static void main(String[] args) throws Exception {

        LinkedList<SocketChannel> clients = new LinkedList<>();

        // 服务端开启监听：接受客户端
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(9090));
        // 重点 OS NONBLOCKING!!! 只让接受客户端，不阻塞
        ss.configureBlocking(false);
        ss.setOption(StandardSocketOptions.TCP_NODELAY, false);

        while (true) {
            // 1、接受客户端的连接
            Thread.sleep(1000);
            // 不会阻塞？ -1 NULL
            SocketChannel client = ss.accept();
            /**
             * accept 调用内核了：
             * 1. 没有客户端连接进来，返回值？在BIO的时候一直卡着，但是在NIO，不卡着，返回-1，NULL
             * 2. 如果来客户端的连接，accept 返回的是这个客户端的fd5，java中就是client object
             * NONBLOCKING 就是代码能往下走了，只不过有不同的情况
             */

            if (client == null) {
                System.out.println("null.....");
            } else {
                // 重点：socket
                // 服务端的listen socket<连接请求三次握手后，往我这里扔，我去通过accept 得到连接的socket>，
                // 连接socket<连接后的数据读写使用的>
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("client...port: " + port);
                clients.add(client);
            }

            // 可以在堆里 堆外
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);

            // 2、遍历已经链接进来的客户端能不能读写数据
            // 串行化！！！！ 多线程！！
            for (SocketChannel c : clients) {
                // >0  -1  0 不会阻塞
                int num = c.read(buffer);
                if (num > 0) {
                    buffer.flip();
                    byte[] aaa = new byte[buffer.limit()];
                    buffer.get(aaa);

                    String b = new String(aaa);
                    System.out.println(c.socket().getPort() + " : " + b);
                    buffer.clear();
                }
            }
        }
    }
}
