package com.fobgochod.problem.solution.dynamic_programming;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 最大子数组和
 * 例1
 * 输入
 * -2,1,-3,4,-1,2,1,-5,4
 * 输出
 * 6
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class LeetCode053 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] digits = scanner.nextLine().split(",");
        int[] ints = Arrays.stream(digits).mapToInt(Integer::valueOf).toArray();
        int maxArea = maxSubArray(ints);
        System.out.println(maxArea);
    }

    public static int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }
}
