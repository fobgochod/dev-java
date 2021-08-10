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
 * RPC客户端
 * 自定义传输协议
 * 粘包拆包的问题，header+body
 *
 * @author Xiao
 * @date 2021/8/10 9:52
 */
public class MyRpcClinet {

    private final Integer num = new Random().nextInt(100);
    private final AtomicInteger count = new AtomicInteger(0);

    /**
     * 模拟Consumer端 && Provider
     */
    @Test
    public void testRpcLocal() {

        new Thread(new MyRpcServer()::rpcServer).start();
        System.out.println("server started...");

        Car car = ProxyFactory.getProxy(Car.class, RpcType.CUSTOM);

        Person person = car.getMan("zhangsan", num);
        System.out.printf("request args: %-2s response data: %-2s\n", num, person);
    }

    @Test
    public void testRpc() {
        Car car = ProxyFactory.getProxy(Car.class, RpcType.CUSTOM);

        String response = car.doCar(num);
        System.out.printf("request args: %-2s response data: %-2s\n", num, response);
    }


    @Test
    public void testRpcConcurrent() throws Exception {

        // new Thread(new MyRpcServer()::rpcServer).start();
        // System.out.println("server started...");

        CountDownLatch latch = new CountDownLatch(Constants.TEST_THREAD_COUNT);

        int clientSize = Constants.TEST_THREAD_COUNT;
        Thread[] threads = new Thread[clientSize];
        for (int i = 0; i < clientSize; i++) {
            threads[i] = new Thread(() -> {
                // 动态代理实现，是真的要去触发 RPC调用吗？
                Car car = ProxyFactory.getProxy(Car.class, RpcType.CUSTOM);

                Integer requestArg = count.incrementAndGet();
                String response = car.doCar(requestArg);

                latch.countDown();
                System.out.printf("client %-10s request args: %-3s response data: %-2s\n", Thread.currentThread().getName(), requestArg, response);
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        latch.await();
    }

}
