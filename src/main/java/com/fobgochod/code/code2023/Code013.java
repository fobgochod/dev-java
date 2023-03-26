package com.fobgochod.code.code2023;

import java.util.Scanner;

public class Code013 {

    /**
     * 50 60 1
     * 50
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] logsArr = in.nextLine().split(" ");

        int pre, now = 0, total = 0, max = 0;
        for (String log : logsArr) {
            int logNum = Integer.parseInt(log);
            pre = total;
            total += logNum;
            if (total >= 100) {
                total = 100;
                now += pre;
                break;
            }
            now += pre;
            max = Math.max(max, total - now);
        }
        System.out.println(Math.max(max, total - now));
    }
}
