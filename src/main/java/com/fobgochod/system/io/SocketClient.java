package com.fobgochod.system.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * C10K 客户端
 *
 * @author fobgochod
 * @date 2021/8/6 13:38
 */
public class SocketClient {

    public static void main(String[] args) throws Exception {
        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 9090);

        try {
            SocketChannel client1 = SocketChannel.open();

            // 192.168.150.1:10000  => 192.168.150.11:9090
            client1.bind(new InetSocketAddress("localhost", 10020));
            client1.connect(serverAddr);

            while (true) {
                client1.write(ByteBuffer.wrap("111\n".getBytes()));
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
