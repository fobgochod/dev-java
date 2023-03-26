package com.fobgochod.java.juc.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程状态
 *
 * @author fobgochod
 * @date 2022/3/13 12:45
 */
public class Thread_02 {

    private static final CountDownLatch latch = new CountDownLatch(1);
    private static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        // 开启线程占住锁lock
        new OccupyLock("occupy-lock").start();

        // 创建线程，NEW状态
        Thread thread = new TestThread("test-thread");
        printState(0, thread);
        // 调用start()方法，RUNNABLE状态
        thread.start();
        printState(1, thread);

        int i = 1;
        while (true) {
            i++;
            TimeUnit.SECONDS.sleep(1);
            printState(i, thread);

            if (i == 3) {
                latch.countDown();
            }
            if (i == 8) {
                LockSupport.unpark(thread);
            }

            if (thread.getState() == Thread.State.TERMINATED) {
                break;
            }
        }
    }

    private static void printState(int num, Thread thread) {
        System.out.printf("%s. [%s] thread state = %s%n", num, Thread.currentThread().getName(), thread.getState());
    }

    private static void printState(String desc, Thread thread) {
        System.out.printf("[%s] %s...%n%n", thread.getName(), desc);
    }

    static class OccupyLock extends Thread {

        public OccupyLock(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (lock) {
                printState("获取到[synchronized]锁", this);
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printState("释放[synchronized]锁", this);
            }
        }
    }

    static class TestThread extends Thread {

        public TestThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            printState("开始执行", this);

            synchronized (lock) {
                printState("获取到[synchronized]锁", this);
            }

            try {
                TimeUnit.SECONDS.sleep(2);
                printState("TimeUnit.SECONDS.sleep()结束", this);
            } catch (InterruptedException ignored) {
            }

            LockSupport.parkNanos(2 * 1000 * 1000 * 1000);
            printState("LockSupport.parkNanos()结束", this);

            LockSupport.park();
            printState("LockSupport.unpark()被执行", this);
        }
    }
}
