package com.fobgochod.java.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConditionTest.java
 *
 * @author fobgochod
 * @date 2021/12/1 23:05
 */
public class ConditionTest {

    public static void main(String[] args) throws Exception {
        BoundedBuffer buffer = new BoundedBuffer();

        //启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "-" + buffer.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "consumer" + i).start();
        }
        TimeUnit.SECONDS.sleep(2);
        //启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    try {
                        buffer.put(Thread.currentThread().getName() + "-" + j);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "producer" + i).start();
        }
    }

    static class BoundedBuffer {
        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();

        final Object[] items = new Object[10];
        int putptr, takeptr, count;

        public void put(Object x) throws InterruptedException {
            lock.lock();
            try {
                while (count == items.length) {
                    notFull.await();
                }
                items[putptr] = x;
                if (++putptr == items.length) {
                    putptr = 0;
                }
                ++count;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public Object take() throws InterruptedException {
            lock.lock();
            try {
                while (count == 0) {
                    notEmpty.await();
                }
                Object x = items[takeptr];
                if (++takeptr == items.length) {
                    takeptr = 0;
                }
                --count;
                notFull.signal();
                return x;
            } finally {
                lock.unlock();
            }
        }
    }
}
