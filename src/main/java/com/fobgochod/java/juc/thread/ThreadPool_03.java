package com.fobgochod.java.juc.thread;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * 线程池达到最大线程后，只要持续有任务，且间隔低于keepAliveTime，就不会有线程会被回收，哪怕大于corePoolSize
 *
 * @author fobgochod
 * @date 2022/3/14 22:34
 */
public class ThreadPool_03 {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2), new DefaultThreadFactory("test"),
                new ThreadPoolExecutor.DiscardPolicy());

        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
        scheduledExecutor.scheduleAtFixedRate(() -> {
            System.out.printf("[%s]---[%s]%n", LocalDateTime.now(), executor);
        }, 0, 2, TimeUnit.SECONDS);

        try {
            //同时提交10个任务,模拟达到最大线程数
            for (int i = 0; i < 5; i++) {
                executor.execute(new Task());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //休眠10秒，打印日志，观察线程池状态
        Thread.sleep(10000);

        //每隔3秒提交一个任务
        while (true) {
            Thread.sleep(3000);
            executor.submit(new Task());
        }
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "-执行任务");
        }
    }
}
