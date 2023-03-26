package com.fobgochod.java.juc.even;

import java.util.concurrent.locks.LockSupport;

public class Problem3 {

    private static final char[] nums = "1234567".toCharArray();
    private static final char[] letters = "ABCDEFG".toCharArray();

    private static Thread t1;
    private static Thread t2;

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            for (char num : nums) {
                System.out.print(num);
                LockSupport.unpark(t2); // 叫醒T2
                LockSupport.park(); // T1阻塞
            }
        });
        t2 = new Thread(() -> {
            for (char letter : letters) {
                LockSupport.park(); // t2阻塞
                System.out.print(letter);
                LockSupport.unpark(t1); // 叫醒t1
            }
        });
        t1.start();
        t2.start();
    }
}
