package com.fobgochod.problem.nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Code75 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // TODO  1. 输入2个字符串
        String str1 = br.readLine();
        String str2 = br.readLine();

        // TODO  2. 转换为字符数组
        char[] c1 = str1.toCharArray();
        char[] c2 = str2.toCharArray();

        // TODO 3. 构建dp数组
        int[][] dp = new int[c1.length + 1][c2.length + 1];

        int res = 0;  // 定义一个结果来保存最长子串
        // TODO 5. 填充数组其余值
        for (int i = 1; i <= c1.length; i++) {
            for (int j = 1; j <= c2.length; j++) {
                // TODO 6. 逐一对比每个字符
                if (c1[i - 1] == c2[j - 1]) { //因为 c1.c2 是从0开始存字符的
                    // TODO 7. 相等则用不包含该字符的上一个最优解去 + 1
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                }
                res = Math.max(res, dp[i][j]);
            }
        }
        System.out.println(res);
    }
}
