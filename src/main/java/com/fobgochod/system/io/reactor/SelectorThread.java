package com.fobgochod.system.io.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 每线程对应一个selector，
 * 多线程情况下，该主机，该程序的并发客户端被分配到多个selector上
 * 注意，每个客户端，只绑定到其中一个selector
 * 其实不会有交互问题
 *
 * @author Xiao
 * @date 2021/8/7 18:32
 */
public class SelectorThread extends ThreadLocal<LinkedBlockingQueue<Channel>> implements Runnable {

    Selector selector = null;
    // LinkedBlockingQueue<Channel> lbq = new LinkedBlockingQueue<>();

    // lbq 在接口或者类中是固定使用方式逻辑写死了。你需要是lbq每个线程持有自己的独立对象
    LinkedBlockingQueue<Channel> channels = get();

    private SelectorThreadGroup group;

    @Override
    protected LinkedBlockingQueue<Channel> initialValue() {
        // 你要丰富的是这里！  pool...
        return new LinkedBlockingQueue<>();
    }

    public SelectorThread(SelectorThreadGroup group) {
        try {
            this.group = group;
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 1. select()
                // System.out.println(Thread.currentThread().getName() + " : before select... " + selector.keys().size());
                // 阻塞  wakeup()
                int nums = selector.select();
                // Thread.sleep(1000);  //这绝对不是解决方案，我只是给你演示
                // System.out.println(Thread.currentThread().getName() + " : after select... " + selector.keys().size());

                //2. 处理selectedKeys
                if (nums > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = keys.iterator();
                    while (iter.hasNext()) {
                        // 线程处理的过程
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            // 复杂，接受客户端的过程（接收之后，要注册，多线程下，新的客户端，注册到那里呢？）
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        } else if (key.isWritable()) {

                        }
                    }
                }

                //3. 处理一些task: listen client
                // 队列是个啥东西啊？堆里的对象，线程的栈是独立，堆是共享的
                if (!channels.isEmpty()) {
                    //只有方法的逻辑，本地变量是线程隔离的
                    Channel channel = channels.take();
                    if (channel instanceof ServerSocketChannel) {
                        ServerSocketChannel server = (ServerSocketChannel) channel;
                        server.register(selector, SelectionKey.OP_ACCEPT);
                        System.out.println(Thread.currentThread().getName() + " : register listen...");
                    } else if (channel instanceof SocketChannel) {
                        SocketChannel client = (SocketChannel) channel;
                        ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                        client.register(selector, SelectionKey.OP_READ, buffer);
                        System.out.println(Thread.currentThread().getName() + " : register client:" + client.getRemoteAddress());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void acceptHandler(SelectionKey key) {
        System.out.println(Thread.currentThread().getName() + " : acceptHandler...");

        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);

            //choose a selector and register!
            group.nextSelectorV3(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readHandler(SelectionKey key) {
        System.out.println(Thread.currentThread().getName() + " : read...");

        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel client = (SocketChannel) key.channel();
        buffer.clear();
        try {
            while (true) {
                int num = client.read(buffer);
                if (num > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        // 将读到的内容翻转，然后直接写出
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (num == 0) {
                    break;
                } else {
                    // 客户端断开了
                    System.out.println(Thread.currentThread().getName() + " : client:" + client.getRemoteAddress() + " closed...");
                    key.cancel();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWorker(SelectorThreadGroup worker) {
        this.group = worker;
    }
}
