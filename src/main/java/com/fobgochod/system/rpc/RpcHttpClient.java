package com.fobgochod.system.rpc;

import com.fobgochod.system.rpc.protocol.RpcType;
import com.fobgochod.system.rpc.proxy.ProxyFactory;
import com.fobgochod.system.rpc.service.Car;
import com.fobgochod.system.rpc.service.Person;
import com.fobgochod.system.rpc.util.Constants;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RPC客户端，基于Http协议进行传输数据
 * <p>
 * 重构考虑
 * FC(function call) -> 函数调用 寻址
 * SC(system call) -> 内核系统调用 中断 int0x80
 * RPC(remote procedure call) -> socket
 * IPC(Inter-Process Communication) -> 管道、信号、socket 跨进程通信
 *
 * @author fobgochod
 * @date 2021/8/9 16:04
 */
public class RpcHttpClient {

    private final Integer num = new Random().nextInt(100);
    private final AtomicInteger count = new AtomicInteger(0);

    @Test
    public void testRpc() throws Exception {
        Car car = ProxyFactory.getProxy(Car.class, RpcType.HTTP_NETTY);
        Person person = car.getMan("zhangsan", num);
        System.out.printf("request args: %-2s response data: %s\n", num, person);
    }

    @Test
    public void testRpcConcurrent() throws Exception {

        CountDownLatch latch = new CountDownLatch(Constants.TEST_THREAD_COUNT);
        int clientSize = Constants.TEST_THREAD_COUNT;
        Thread[] threads = new Thread[clientSize];
        for (int i = 0; i < clientSize; i++) {
            threads[i] = new Thread(() -> {
                Car car = ProxyFactory.getProxy(Car.class, RpcType.HTTP_NETTY_MULTIPLEXING);

                Integer age = count.incrementAndGet();
                Person person = car.getMan("zhangsan", age);

                latch.countDown();
                System.out.printf("client %-10s request args: %-2s response data: %s\n", Thread.currentThread().getName(), age, person);
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        latch.await();
    }
}
