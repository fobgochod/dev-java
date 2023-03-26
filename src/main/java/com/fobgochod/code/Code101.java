package com.fobgochod.code;

import java.util.Arrays;
import java.util.Scanner;

public class Code101 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();//接收数组长度
            int[] arr = new int[n];//创建数组

            for (int i = 0; i < n; i++) {//数组填入
                arr[i] = sc.nextInt();
            }

            int flag = sc.nextInt();//接收排序标识
            Arrays.sort(arr);//数组排序

            if (flag == 0) {//正序输出
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i] + " ");
                }
            } else {//逆序输出
                for (int i = arr.length - 1; i >= 0; i--) {
                    System.out.print(arr[i] + " ");
                }
            }
        }
    }
}
