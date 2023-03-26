package com.fobgochod.java.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatchTest.java
 *
 * @author fobgochod
 * @date 2021/12/1 22:03
 */
public class CountDownLatchTest {

    private static final CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) throws Exception {
        System.out.println("in " + Thread.currentThread().getName() + "...");
        System.out.println("before latch.await()...");
        new Test(10).start();
        new Test(20).start();
        new Test(100).start();
        latch.await();
        System.out.println("in " + Thread.currentThread().getName() + "...");
        System.out.println("after latch.await()...");
    }

    static class Test extends Thread {
        private final long sleep;

        public Test(long sleep) {
            this.sleep = sleep;
        }

        @Override
        public void run() {
            System.out.println("enter Thread " + Thread.currentThread().getName() + "...");
            System.out.println("execute countdown...");

            try {
                TimeUnit.SECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("exit Thread" + Thread.currentThread().getName() + ".");
        }
    }
}
