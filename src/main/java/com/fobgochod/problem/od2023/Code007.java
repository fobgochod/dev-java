package com.fobgochod.problem.od2023;

import java.util.Scanner;

public class Code007 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int i = 0; i < T; i++) {
            int n = sc.nextInt();
            long k = sc.nextLong();

            long charNum = (long) Math.pow(2, n - 1);       //第n行的总字符个数
            int re = doProcess(charNum, k, 0);
            if (re % 2 == 0) {
                System.out.println("red");      //翻转次数为2的倍数
            } else {
                System.out.println("blue");
            }
        }

    }

    /**
     * 翻转次数
     *
     * @param count   总字符的个数
     * @param cur     字符的索引
     * @param reverse 翻转次数
     * @return
     */
    public static int doProcess(long count, long cur, int reverse) {

        if (count == 1) {
            return reverse;
        }
        long half = count / 2;
        if (cur < half) {    //小于半数说明是经过翻转的部分
            reverse++;
            return doProcess(half, cur, reverse);
        } else {
            return doProcess(half, cur - half, reverse);
        }

    }
}
