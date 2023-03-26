package com.fobgochod.problem.od2023;

import java.util.Arrays;
import java.util.Scanner;

/**
 * MVP争夺战
 * <p>
 * 例1
 * 输入
 * 9
 * 5 2 1 5 2 1 5 2 1
 * 输出
 * 6
 */
public class Code048 {

    static int[] scoreTable = new int[51];
    static int maxScore = -1;
    static int minScore = 51;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        sc.nextLine();
        String[] p = sc.nextLine().split(" ");
        int[] ints = Arrays.stream(p).mapToInt(Integer::parseInt).toArray();

        int total = 0;
        maxScore = Arrays.stream(ints).max().getAsInt();
        minScore = Arrays.stream(ints).min().getAsInt();
        //原代码95分，唯一错误用例20 1 2 3 4 5 6 7 8 9 21 23 24 25 26 28 27 26 29
        //所有这边的边界不能用t，只能用得分p的长度
        for (int i = 0; i < ints.length; i++) {
            scoreTable[ints[i]]++;
            total += ints[i];
        }

        for (int i = maxScore; i <= total / 2; ++i) {
            if (total % i == 0) {
                dfs(total / i, 0, i, maxScore);
            }
        }

        System.out.println(total);
    }

    public static void dfs(int mvpCount, int tempSum, int mvpScore, int maxScore) {
        if (mvpCount == 0) {
            System.out.println(mvpScore);
            System.exit(0);
        }

        if (tempSum == mvpScore) {
            dfs(mvpCount - 1, 0, mvpScore, Code048.maxScore);
            return;
        }

        for (int i = maxScore; i >= minScore; --i) {
            if (scoreTable[i] > 0 && (i + tempSum) <= mvpScore) {
                scoreTable[i]--;
                dfs(mvpCount, tempSum + i, mvpScore, i);
                scoreTable[i]++;
                if (tempSum == 0 || (tempSum + i) == mvpScore) {
                    break;
                }
            }
        }
    }
}
