package com.fobgochod.problem.od2023;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 数组的中心位置
 * 例1
 * 输入
 * 2 5 3 6 5 6
 * 输出
 * 3
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class Code014 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String[] strings = sc.nextLine().split(" ");
        int len = strings.length;
        int[] nums = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();

        //题目说：数组第一个元素的左侧积为1，最后一个元素的右侧积为1
        int left = 1, right = 1;
        for (int num : nums) {
            right = right * num;    //数组的总乘积
        }

        boolean flag = false;
        for (int i = 0; i < len; i++) {
            if (i != 0) {
                left = left * nums[i - 1];    //左侧积做乘法
            }
            right = right / nums[i];    //右侧积做除法
            if (left == right) {
                System.out.print(i);
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println(-1);
        }
    }

}
