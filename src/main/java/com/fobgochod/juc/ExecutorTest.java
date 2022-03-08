package com.fobgochod.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auther: Xiao
 * @date: 2021/11/25 23:22
 * @description: 功能描述
 */
public class ExecutorTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = new ThreadPoolExecutor(2, 4, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(2), Executors.defaultThreadFactory(), new Reject());

        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new Test());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        executor.shutdown();
    }


    private static class Reject implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new RuntimeException("线程池已满：" + executor.toString());
        }
    }

    private static class Test extends Thread {
        @Override
        public void run() {
            try {
                System.out.println(getName() + " 开始工作。。。");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
