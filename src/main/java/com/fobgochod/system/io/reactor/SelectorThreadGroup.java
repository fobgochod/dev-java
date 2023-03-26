package com.fobgochod.system.io.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分为Boss和Worker两个组
 * Boss:专门负责处理listen
 * Worker: 负责处理client进行R/W
 * <p>
 * 默认是Boss，Boss必须持有Worker，默认持有自己
 *
 * @author fobgochod
 * @date 2021/8/7 18:44
 */
public class SelectorThreadGroup {

    private final AtomicInteger num = new AtomicInteger(0);
    private final SelectorThread[] selectors;
    private SelectorThreadGroup worker = this;
    ServerSocketChannel server = null;

    public void setWorker(SelectorThreadGroup worker) {
        this.worker = worker;
    }

    SelectorThreadGroup(int num) {
        // num 线程数
        selectors = new SelectorThread[num];
        for (int i = 0; i < num; i++) {
            selectors[i] = new SelectorThread(this);
            new Thread(selectors[i]).start();
        }
    }

    public void bind(int port) {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // 注册到那个selector上呢？
            // nextSelectorV2(server);
            nextSelectorV3(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 混杂模式
     * 只有一个线程负责accept，每个都会被分配client，进行R/W
     *
     * @param channel
     */
    public void nextSelector(Channel channel) {
        // 在 main线程中，取到堆里的selectorThread对象
        SelectorThread selectorThread = next();

        // 1. 通过队列传递数据 消息
        selectorThread.channels.add(channel);
        // 2.通过打断阻塞，让对应的线程去自己在打断后完成注册selector
        selectorThread.selector.wakeup();
    }

    /**
     * 写死
     * 固定第一个处理accept，
     * 其它每个都会被分配client,进行R/W
     *
     * @param channel
     */
    public void nextSelectorV2(Channel channel) {
        try {
            if (channel instanceof ServerSocketChannel) {
                selectors[0].channels.put(channel);
                selectors[0].selector.wakeup();
            } else {
                // 在 main线程种，取到堆里的selectorThread对象
                SelectorThread selectorThread = nextV2();

                // 1. 通过队列传递数据 消息
                selectorThread.channels.add(channel);
                // 2. 通过打断阻塞，让对应的线程去自己在打断后完成注册selector
                selectorThread.selector.wakeup();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Boss处理accept，
     * Worker处理client,进行R/W
     *
     * @param channel
     */
    public void nextSelectorV3(Channel channel) {
        try {
            if (channel instanceof ServerSocketChannel) {
                SelectorThread selectorThread = next();
                selectorThread.channels.put(channel);

                // listen选择了boss组中的一个线程后，要更新这个线程的worker组
                selectorThread.setWorker(worker);

                selectorThread.selector.wakeup();
            } else {
                // 在main线程种，取到堆里的selectorThread对象
                SelectorThread selectorThread = nextV3();

                //1. 通过队列传递数据 消息
                selectorThread.channels.add(channel);
                //2. 通过打断阻塞，让对应的线程去自己在打断后完成注册selector
                selectorThread.selector.wakeup();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * wakeup放在register方法之前之后都不行
     *
     * @param channel
     */
    public void nextSelectorV0(Channel channel) {
        SelectorThread selectorThread = next();

        // 重点：c有可能是server 有可能是client
        ServerSocketChannel server = (ServerSocketChannel) channel;
        // 呼应上，int nums = selectorThread.select();  //阻塞  wakeup()
        try {
            // selectorThread.selector.wakeup();
            // 会被阻塞的!!!!!
            server.register(selectorThread.selector, SelectionKey.OP_ACCEPT);
            // 功能是让selector的select()方法，立刻返回，不阻塞！
            selectorThread.selector.wakeup();
            System.out.println("...");
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    // 无论 serverSocket、socket都复用这个方法
    private SelectorThread next() {
        // 轮询就会很尴尬，倾斜
        int index = num.incrementAndGet() % selectors.length;
        return selectors[index];
    }

    private SelectorThread nextV2() {
        // 轮询就会很尴尬，倾斜
        int index = num.incrementAndGet() % (selectors.length - 1);
        return selectors[index + 1];
    }

    private SelectorThread nextV3() {
        // 动用worker的线程分配
        int index = num.incrementAndGet() % worker.selectors.length;
        return worker.selectors[index];
    }
}
