package com.fobgochod.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @auther: Xiao
 * @date: 2021/12/6 22:11
 * @description: 功能描述
 */
public class ThreadTest {

    private static final CountDownLatch latch = new CountDownLatch(1);
    private static final Object lock = new Object();

    public static void main(String[] args) throws Exception {

        new Temp().start();

        Thread thread = new TestThread();
        System.out.println("threadStatus = " + thread.getState());
        thread.start();

        int i = 0;
        while (true) {
            i++;
            System.out.println("threadStatus = " + thread.getState());
            TimeUnit.SECONDS.sleep(1);
            if (i == 2) {
                latch.countDown();
            }
            if (i == 8) {
                LockSupport.unpark(thread);
            }

            if (thread.getState() == Thread.State.TERMINATED) {
                System.out.println("threadStatus = " + thread.getState());
                break;
            }
        }

    }

    static class Temp extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class TestThread extends Thread {
        @Override
        public void run() {
            System.out.println("getName() 开始执行...");

            synchronized (lock) {
                System.out.println(getName() + " synchronized");
            }

            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(getName() + " TimeUnit.SECONDS.sleep(1)");
            } catch (InterruptedException ignored) {
            }

            LockSupport.parkNanos(2 * 1000 * 1000 * 1000);
            System.out.println(getName() + " LockSupport.parkNanos(1)");

            LockSupport.park();
        }
    }
}
