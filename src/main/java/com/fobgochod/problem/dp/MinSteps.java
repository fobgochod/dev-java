package com.fobgochod.problem.dp;

/**
 * 给定一个矩阵m，从左上角开始每次只能向右走或者向下走
 * 最后达到右下角的位置，路径中所有数字累加起来就是路径和，
 * 返回所有路径的最小路径和
 * <p>
 * 1 3 5 9
 * 8 1 3 4
 * 5 0 6 1
 * 8 8 4 0
 * <p>
 * 最小路径
 * 0 0  0  0  0
 * 0 1  4  9  18
 * 0 9  5  8  12
 * 0 14 5  11 12
 * 0 22 13 15 12
 * <p>
 * 1 3 1 0 6 1 0
 */
public class MinSteps {

    public static void main(String[] args) {
        int[][] arr = {{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}, {8, 8, 4, 0}};
        int[][] dp = new int[5][5];

//        minStep(arr, dp);
        maxStep(arr, dp);
        System.out.println("dp = " + dp[4][4]);

    }

    private static void minStep(int[][] arr, int[][] dp) {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                int up = dp[i - 1][j];
                int right = dp[i][j - 1];

                if (i == 1 && j > 1) {
                    // 上边界 只能来源于左侧
                    up = Integer.MAX_VALUE;
                }
                if (j == 1 && i > 1) {
                    // 坐边界 只能来源与上侧
                    right = Integer.MAX_VALUE;
                }
                dp[i][j] = Math.min(up, right) + arr[i - 1][j - 1];
            }
        }
    }

    private static void maxStep(int[][] arr, int[][] dp) {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                int up = dp[i - 1][j];
                int right = dp[i][j - 1];
                dp[i][j] = Math.max(up, right) + arr[i - 1][j - 1];
            }
        }
    }
}
