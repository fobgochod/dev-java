package com.fobgochod.juc.even;

import java.util.ArrayList;
import java.util.List;

public class Problem1 {

    private static final int TOTAL_SIZE = 10;
    private static final int OUT_SIZE = 5;
    private List<Object> lists = new ArrayList<>(TOTAL_SIZE);

    public static void main(String[] args) {
        Problem1 p = new Problem1();
        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t1 begin...");
                for (int i = 0; i < TOTAL_SIZE; i++) {
                    p.add(i);
                    System.out.println("t1 add " + i);
                    if (p.size() == OUT_SIZE) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("t1 end...");
            }
        }).start();

        new Thread(() -> {
            System.out.println("t2 begin...");
            synchronized (lock) {
                try {
                    if (p.size() != OUT_SIZE) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 end...");
                lock.notify();
            }
        }).start();
    }

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }
}
