package com.fobgochod.problem.od2023;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 日志采集系统
 * 例1
 * 输入
 * 1 98 1
 * 输出
 * 98
 * <p>
 * 示例2
 * 输入
 * 50 60 1
 * 输出
 * 50
 */
public class Code013 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] logsArr = in.nextLine().split(" ");

        int[] nums = Arrays.stream(logsArr).mapToInt(Integer::valueOf).toArray();

        int sum = 0;
        int maxScore = 0;
        int temp = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            sum += num;
            if (sum >= 100) {
                temp = 100 - minus(nums, i);
                maxScore = Math.max(maxScore, temp);
                break;
            }
            temp = sum - minus(nums, i);
            maxScore = Math.max(maxScore, temp);
        }

        System.out.println(maxScore);

    }

    private static int minus(int[] nums, int num) {
        int minus = 0;
        for (int j = 0; j < num; j++) {
            minus += nums[j] * (num - j);
        }
        return minus;
    }
}
