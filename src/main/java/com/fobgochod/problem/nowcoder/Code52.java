package com.fobgochod.problem.nowcoder;

import java.util.Scanner;

public class Code52 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            char[] from = scanner.nextLine().toCharArray();
            char[] to = scanner.nextLine().toCharArray();

            int[][] dp = new int[from.length + 1][to.length + 1];

            for (int i = 1; i <= from.length; i++) {
                dp[i][0] = i;
            }


            for (int i = 1; i <= to.length; i++) {
                dp[0][i] = i;
            }

            for (int i = 1; i <= from.length; i++) {
                for (int j = 1; j <= to.length; j++) {
                    if (from[i - 1] == to[j - 1]) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        int i1 = dp[i][j - 1] + 1;
                        int i2 = dp[i - 1][j] + 1;
                        int i3 = dp[i - 1][j - 1] + 1;
                        dp[i][j] = Math.min(i1, Math.min(i2, i3));
                    }
                }
            }
            System.out.println(dp[from.length][to.length]);
        }
    }
}
