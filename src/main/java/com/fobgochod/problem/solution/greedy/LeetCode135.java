package com.fobgochod.problem.solution.greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 分发糖果
 * 例1
 * 输入
 * 1,2,2
 * 输出
 * 4
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class LeetCode135 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] digits = scanner.nextLine().split(",");
        int[] ints = Arrays.stream(digits).mapToInt(Integer::valueOf).toArray();
        int candy = candy(ints);
        System.out.println(candy);
    }

    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        int right = 0, ret = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            ret += Math.max(left[i], right);
        }
        return ret;
    }
}
