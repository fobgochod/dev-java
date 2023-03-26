package com.fobgochod.java.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @auther: Xiao
 * @date: 2021/12/2 22:09
 * @description: 功能描述
 */
public class InterruptTest {

    public static void main(String[] args) throws Exception {
        Thread wait = new WaitThread();
        Thread park = new ParkThread();
        wait.start();
        park.start();

        TimeUnit.SECONDS.sleep(3);

        wait.interrupt();
        park.interrupt();
    }

    static class WaitThread extends Thread {

        public WaitThread() {
            super("WaitThread");
        }

        @Override
        public void run() {
            synchronized (this) {
                try {
                    System.out.println(Thread.currentThread().getName() + " start...");
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupt exception " + e.getClass());
                    e.printStackTrace();
                }
            }
        }
    }

    static class ParkThread extends Thread {

        public ParkThread() {
            super("ParkThread");
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " start...");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + " interrupt end...");
        }
    }
}
