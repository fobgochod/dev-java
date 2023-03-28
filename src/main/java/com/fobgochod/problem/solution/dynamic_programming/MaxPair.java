package com.fobgochod.problem.solution.dynamic_programming;

public class MaxPair {

    /**
     * ABAKK -> ABA
     */
    public static void main(String[] args) {
        String s1 = "abaaab";

        char[] c1 = s1.toCharArray();
        char[] c2 = reverse(c1);

        int[][] dp = new int[c1.length + 1][c2.length + 1];

        int max = 0;
        for (int i = 1; i <= c1.length; i++) {
            for (int j = 1; j <= c2.length; j++) {
                if (c1[i - 1] == c2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                max = Math.max(max, dp[i][j]);
            }
        }


        System.out.println("dp = " + max);

    }

    private static char[] reverse(char[] arrs) {
        char[] newArrs = new char[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            newArrs[i] = arrs[arrs.length - i-1];
        }
        return newArrs;
    }
}
