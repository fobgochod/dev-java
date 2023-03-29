package com.fobgochod.problem.solution.two_pointers;


import java.util.Arrays;
import java.util.Scanner;

/**
 * 盛最多水的容器
 * 例1
 * 输入
 * 1,8,6,2,5,4,8,3,7
 * 输出
 * 49
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class LeetCode011 {

    public static void main(String[] args) {
        int[] nums = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int maxArea = maxArea(nums);
        System.out.println(maxArea);
    }


    public static int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int ans = 0;
        while (l < r) {
            int area = Math.min(height[l], height[r]) * (r - l);
            ans = Math.max(ans, area);
            if (height[l] <= height[r]) {
                ++l;
            } else {
                --r;
            }
        }
        return ans;
    }
}
