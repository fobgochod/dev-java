package com.fobgochod.java.juc.even;

public class Problem4 {

    private static final char[] nums = "1234567".toCharArray();
    private static final char[] letters = "ABCDEFG".toCharArray();
    private static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                for (char num : nums) {
                    System.out.print(num);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
                lock.notify();  // 必须，否则无法停止程序
            }

        }).start();

        new Thread(() -> {
            synchronized (lock) {
                for (char letter : letters) {
                    System.out.print(letter);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
                lock.notify();
            }
        }).start();
    }
}
