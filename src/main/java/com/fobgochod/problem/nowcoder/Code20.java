package com.fobgochod.problem.nowcoder;

import java.util.Arrays;
import java.util.Scanner;

public class Code20 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            //同学的总数
            int n = sc.nextInt();
            //N位同学的身高
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            //dp1[i]表示该位置结尾的最长递增子序列长度
            int[] dp1 = new int[n];
            //给dp1数组赋初值
            Arrays.fill(dp1, 1);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    //如果i位置同学身高大于j位置同学，则可以排在j位置同学后面
                    if (arr[j] < arr[i]) {
                        dp1[i] = Math.max(dp1[i], dp1[j] + 1);
                    }
                }
            }

            //dp2[i]表示该位置开头的最长递减子序列长度
            int[] dp2 = new int[n];
            //给dp2数组赋初值
            Arrays.fill(dp2, 1);
            for (int i = n - 1; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) {
                    /*如果i位置同学身高大于j位置同学，则可以排在j位置同学后面
                    反过来，则是j可排在i同学后面，构成递减子序列*/
                    if (arr[j] < arr[i]) {
                        dp2[i] = Math.max(dp2[i], dp2[j] + 1);
                    }
                }
            }

            //统计每个位置的合唱队形长度最大值
            int max = 0;
            for (int i = 0; i < n; i++) {
                max = Math.max(max, dp1[i] + dp2[i] - 1);
            }

            System.out.println(n - max);
        }
    }
}
