package com.fobgochod.java.juc.even;


import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class SyncVsAtomicVsLongAdder {

    public static final int THREADS = 1000;
    public static final int COUNT = 100000;

    private static Long aLong = 0L;
    private static AtomicLong atomicLong = new AtomicLong(0L);
    private static LongAdder longAdder = new LongAdder();


    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[THREADS];
        mLong(threads);
        mAtomicLong(threads);
        mLongAdder(threads);
    }

    private static void mLong(Thread[] threads) throws InterruptedException {
        long start = System.currentTimeMillis();
        final Object o = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < COUNT; j++) {
                    synchronized (o) {
                        aLong++;
                    }
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("count = " + aLong + " Long = " + (System.currentTimeMillis() - start));
    }

    private static void mAtomicLong(Thread[] threads) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < COUNT; j++) {
                    atomicLong.incrementAndGet();
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("count2 = " + atomicLong + " AtomicLong = " + (System.currentTimeMillis() - start));
    }

    private static void mLongAdder(Thread[] threads) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < COUNT; j++) {
                    longAdder.increment();
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("count3 = " + longAdder + " LongAdder = " + (System.currentTimeMillis() - start));
    }
}
