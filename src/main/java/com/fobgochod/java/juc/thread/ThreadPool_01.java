package com.fobgochod.java.juc.thread;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * ExecutorTest.java
 *
 * @author Xiao
 * @date 2022/3/15 22:32
 */
public class ThreadPool_01 {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5), Executors.defaultThreadFactory(), new MyReject());
        executor.allowCoreThreadTimeOut(false);

        //每隔两秒打印线程池的信息
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
        scheduledExecutor.scheduleAtFixedRate(() -> {
            System.out.printf("[%s]---[%s]%n", LocalDateTime.now(), executor);
        }, 0, 2, TimeUnit.SECONDS);

        for (int i = 0; i < 11; i++) {
            executor.execute(new Test());
        }
    }


    private static class MyReject implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new RuntimeException("线程池已满：" + executor.toString());
        }
    }

    private static class Test extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.printf("[%s] 工作完成...%n", getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
