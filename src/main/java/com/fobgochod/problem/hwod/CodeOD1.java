package com.fobgochod.problem.hwod;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 题目
 * 新员工座位安排系统
 * 例1
 * 输入
 * 0 1 0
 * 输出
 * 1
 * <p>
 * 示例2
 * 输入
 * 1 1 0 1 2 1 0
 * 输出
 * 3
 */
public class CodeOD1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arrays = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::valueOf).toArray();

        int max = sw(arrays, arrays.length);
        int max2 = dp(arrays, arrays.length);

        System.out.println(max);
        System.out.println(max2);
    }

    private static int sw(int[] arrays, int length) {
        int max = 0, left = 0, right = 0;
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] == 1) {
                left++;
            } else if (arrays[i] == 2) {
                left = 0;
            } else if (arrays[i] == 0) {
                for (int j = i + 1; j < arrays.length; j++) {
                    if (arrays[j] == 1) {
                        right++;
                    } else if (arrays[j] == 0 || arrays[j] == 2) {
                        break;
                    }
                }
                if (max < left + right) {
                    max = left + right;
                }
                right = 0;
                left = 0;
            }
        }
        return max;
    }

    private static int dp(int[] arrays, int length) {
        int[] dpLeft = new int[length + 1];
        int[] dpRight = new int[length + 1];
        for (int i = 0; i < length; i++) {
            if (arrays[i] == 0 || arrays[i] == 2) {
                dpLeft[i + 1] = 0;
            } else {
                dpLeft[i + 1] = dpLeft[i] + 1;
            }
        }

        for (int i = length - 1; i >= 0; i--) {
            if (arrays[i] == 0 || arrays[i] == 2) {
                dpRight[i] = 0;
            } else {
                dpRight[i] = dpRight[i + 1] + 1;
            }
        }

        int max = 0;
        for (int i = 0; i < length; i++) {
            int temp = dpLeft[i + 1] + dpRight[i];
            max = Math.max(temp, max);
        }
        return max;
    }
}
