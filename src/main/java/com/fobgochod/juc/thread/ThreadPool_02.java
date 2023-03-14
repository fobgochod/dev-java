package com.fobgochod.juc.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 当执行方式是execute时，可以看到堆栈异常的输出。
 * 当执行方式是submit时，堆栈异常没有输出。但是调用Future.get()方法时，可以捕获到异常。
 * 不会影响线程池里面其他线程的正常执行。
 * 当执行方式是execute时，线程池会把这个线程移除掉，并创建一个新的线程放到线程池中。
 *
 * @author Xiao
 * @date 2022/3/15 22:38
 */
public class ThreadPool_02 {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        executorService.execute(() -> sayHi("execute"));
        executorService.execute(() -> sayHi("execute"));
        executorService.execute(() -> sayHi("exception"));
        Thread.sleep(100);
        executorService.execute(() -> sayHi("execute"));
        executorService.execute(() -> sayHi("execute"));
    }

    private static void sayHi(String name) {
        String printStr = "【thread-name:" + Thread.currentThread().getName() + ",执行方式:" + name + "】";
        System.out.println(printStr);
        if ("exception".equals(name)) {
            throw new RuntimeException(printStr + ",我异常啦!哈哈哈!");
        }
    }
}
