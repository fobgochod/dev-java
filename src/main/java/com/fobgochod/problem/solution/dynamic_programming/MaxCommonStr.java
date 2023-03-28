package com.fobgochod.problem.solution.dynamic_programming;

public class MaxCommonStr {

    public static void main(String[] args) {
        String s1 = "asdfas";
        String s2 = "werasdfaswer";

        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();

        int[][] dp = new int[arr1.length + 1][arr2.length + 1];

        int max = 0;
        for (int i = 1; i <= arr1.length; i++) {
            for (int j = 1; j <= arr2.length; j++) {
                if (arr1[i - 1] == arr2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                max = Math.max(max, dp[i][j]);
            }
        }

        System.out.println("dp = " + max);
    }
}
