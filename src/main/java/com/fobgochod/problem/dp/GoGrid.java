package com.fobgochod.problem.dp;


/**
 * 从坐标(0,0)走到(3,3) 有多少种方法
 * 只能从上到下 左到右
 * <p>
 * 1 3 5 9
 * 8 1 3 4
 * 5 0 6 1
 * 8 8 4 0
 * <p>
 * 最小路径
 * 0 0 0  0  0
 * 0 1 1  1  1
 * 0 1 2  3  4
 * 0 1 3  6 10
 * 0 1 4 10 20
 * <p>
 */
public class GoGrid {

    public static void main(String[] args) {

        int[][] dp = new int[5][5];


        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (i == 1 && j == 1) {
                    dp[1][1] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        System.out.println("dp = " + dp[4][4]);
    }

}
