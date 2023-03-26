package com.fobgochod.code.code2023;

import java.util.Arrays;
import java.util.Scanner;

public class Code002 {

    /**
     * 实例1：
     * 5 3
     * 4 5 3 5 5
     * <p>
     * 5
     * 实例2：
     * 5 2
     * 4 5 3 5 5
     * <p>
     * 4
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 数组长度
        int arrLen = sc.nextInt();
        // 物料长度
        int len = sc.nextInt();
        //
        int[] arr = new int[arrLen];

        for (int j = 0; j < arrLen; j++) {
            arr[j] = sc.nextInt();
        }

        Arrays.sort(arr);
        //动态计算
        dynamicCal(arr, len);
        System.out.println(arr[0]);
    }

    public static void dynamicCal(int[] arr, int len) {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (j + 1 < arr.length && arr[j] + 1 > arr[j + 1]) {
                    continue;
                }
                arr[j]++;
                break;
            }
        }
    }
}
