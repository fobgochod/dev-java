package com.fobgochod.code.middle;

import java.util.Scanner;

public class Code92 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            char[] chars = str.toCharArray();
            int len = chars.length;
            int[] dp = new int[len + 1];
            int res = 0;
            for (int i = 1; i <= len; i++) {
                if (chars[i - 1] >= '0' && chars[i - 1] <= '9') {
                    dp[i] = dp[i - 1] + 1;
                    res = Math.max(res, dp[i]);
                }
            }

            for (int i = 1; i <= len; i++) {
                if (dp[i] == res) {
                    System.out.print(str.substring(i - res, i));
                }
            }
            System.out.println("," + res);
        }
    }
}
