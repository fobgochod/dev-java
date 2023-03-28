package com.fobgochod.problem.solution.dynamic_programming;

import java.util.Scanner;

public class Levenshtein {

    /**
     * 字符串A: abcdefg
     * 字符串B: abcdef
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str1 = sc.nextLine();
            String str2 = sc.nextLine();
            if (str1 != null) {
                System.out.println(getDistance(str1, str2));
            }
        }
    }

    public static int getDistance(String str1, String str2) {
        char[] wd1 = str1.toCharArray();
        char[] wd2 = str2.toCharArray();
        int len1 = wd1.length;
        int len2 = wd2.length;
        //定义一个矩阵
        int[][] dist = new int[len1 + 1][len2 + 1];
        //初始状态 F(i, 0) = i; F(0, j) = j
        for (int i = 0; i <= len1; ++i) {
            dist[i][0] = i;
        }
        for (int j = 0; j <= len2; ++j) {
            dist[0][j] = j;
        }
        for (int i = 1; i <= len1; ++i) {
            for (int j = 1; j <= len2; ++j) {
                //F(i,j) = min(F(i-1, j) + 1,
                // F(i, j-1) + 1, F(i-1, j-1) + (wd1[i] == wd2[j] ? 0 : 1))
                // 首先求出插入和删除的最小值
                dist[i][j] = Math.min(dist[i - 1][j], dist[i][j - 1]) + 1;
                //再和替换进行比较
                if (wd1[i - 1] == wd2[j - 1]) {
                    //不需要进行替换
                    dist[i][j] = Math.min(dist[i][j], dist[i - 1][j - 1]);
                } else {
                    dist[i][j] = Math.min(dist[i][j], dist[i - 1][j - 1] + 1);
                }
            }
        }
        return dist[len1][len2];
    }
}
