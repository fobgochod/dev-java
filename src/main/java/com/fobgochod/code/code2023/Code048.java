package com.fobgochod.code.code2023;

import java.util.Arrays;
import java.util.Scanner;

public class Code048 {

    static int[] cnt = new int[51];
    static int maxn = -1;
    static int minn = 51;

    /**
     * 9
     * 5 2 1 5 2 1 5 2 1
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        sc.nextLine();
        String[] p = sc.nextLine().split(" ");
        int[] ints = Arrays.stream(p).mapToInt(Integer::parseInt).toArray();

        int total = 0;
        maxn = Arrays.stream(ints).max().getAsInt();
        minn = Arrays.stream(ints).min().getAsInt();
        //原代码95分，唯一错误用例20 1 2 3 4 5 6 7 8 9 21 23 24 25 26 28 27 26 29
        //所有这边的边界不能用t，只能用得分p的长度
        for (int i = 0; i < ints.length; i++) {
            cnt[ints[i]]++;
            total += ints[i];
        }

        for (int i = maxn; i <= total / 2; ++i) {
            if (total % i == 0) {
                dfs(total / i, 0, i, maxn);
            }
        }

        System.out.println(total);
    }

    public static void dfs(int res, int sum, int target, int p) {
        if (res == 0) {
            System.out.println(target);
            System.exit(0);
        }

        if (sum == target) {
            dfs(res - 1, 0, target, maxn);
            return;
        }

        for (int i = p; i >= minn; --i) {
            if (cnt[i] > 0 && (i + sum) <= target) {
                cnt[i]--;
                dfs(res, sum + i, target, i);
                cnt[i]++;
                if (sum == 0 || (sum + i) == target) {
                    break;
                }
            }
        }
    }
}
