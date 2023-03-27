package com.fobgochod.problem.od2023;

import java.util.Scanner;

/**
 * 简单的自动曝光
 * 例1
 * 输入
 * 129 130 129 130
 * 输出
 * -2
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class Code012 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String[] s = sc.nextLine().split(" ");
        int n = s.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(s[i]);
        }
        //所有数字的和
        int sum = 0;
        for (int v : a) sum += v;
        //和与中位数的差值
        int ans = Math.abs(sum - 128 * n);
        int res = 0;
        for (int i = -255; i <= 255; i++) {
            int t = 0;
            for (int j = 0; j < n; j++) {
                //模拟出k值为-255--255的所有可能和
                t += Math.max(Math.min(a[j] + i, 255), 0);
            }
            int dis = Math.abs(t - 128 * n);
            //找出差值最小的k值
            if (dis < ans) {
                ans = dis;
                res = i;
            }
        }
        System.out.println(res);
    }
}
