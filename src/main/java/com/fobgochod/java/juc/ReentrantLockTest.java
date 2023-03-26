package com.fobgochod.java.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TestAqs.java
 *
 * @author Xiao
 * @date 2021/12/1 21:34
 */
public class ReentrantLockTest {

    private static final ReentrantLock lock = new ReentrantLock(true);
    private static int i;

    public static void main(String[] args) throws Exception {
        new Test("Thread-T1").start();
        new Test("Thread-T2").start();
    }


    static class Test extends Thread {

        public Test(String name) {
            super(name);
        }

        @Override
        public void run() {
            lock.lock();
            try {
                i++;
                try {
                    System.out.println("lock = " + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
