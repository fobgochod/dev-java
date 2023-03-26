package com.fobgochod.java.juc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建线程
 *
 * @author fobgochod
 * @date 2022/3/13 15:35
 */
public class Thread_01 {

    public static void main(String[] args) {
        T1 t1 = new T1();
        t1.start();

        Thread t2 = new Thread(new T2());
        t2.start();

        Thread t3 = new Thread(() -> {
            System.out.println("T3 = " + Thread.currentThread().getName());
        });
        t3.start();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(() -> {
            System.out.println("T4 = " + Thread.currentThread().getName());
        });
        executor.shutdown();
    }

    private static class T1 extends Thread {
        @Override
        public void run() {
            System.out.println("T1 = " + Thread.currentThread().getName());
        }
    }

    private static class T2 implements Runnable {
        @Override
        public void run() {
            System.out.println("T2 = " + Thread.currentThread().getName());
        }
    }
}
