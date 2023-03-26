package com.fobgochod.problem.od;

import java.util.Scanner;

/**
 * 微服务的集成测试
 * 例1
 * 输入
 * 3
 * 5 0 0
 * 1 5 0
 * 0 1 5
 * 3
 * 输出
 * 15 = 5+5+5
 * 例2
 * 5
 * 1 0 0 0 0
 * 0 2 0 0 0
 * 1 1 3 0 0
 * 1 1 0 4 0
 * 0 0 1 1 5
 * 5
 * 输出
 * 11 = 2+4+5
 */
public class Code046 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] nums = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nums[i][j] = sc.nextInt();
            }
        }
        int k = sc.nextInt();
        int count = 0;
        count = dfs(nums, k - 1);
        System.out.println(count);
    }

    private static int dfs(int[][] nums, int k) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[k][i] != 0 && i != k) {
                max = Math.max(max, dfs(nums, i));
            }
        }
        return max + nums[k][k];
    }
}
