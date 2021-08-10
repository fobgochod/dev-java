package com.fobgochod.system.rpc.transport;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端连接池
 *
 * @author Xiao
 * @date 2021/8/9 16:17
 */
public class ClientPool {

    NioSocketChannel[] clients;
    Object[] lock;

    ClientPool(int size) {
        // init 初始化  连接都是空的
        clients = new NioSocketChannel[size];
        // 锁是可以初始化的
        lock = new Object[size];
        for (int i = 0; i < size; i++) {
            lock[i] = new Object();
        }
    }
}
