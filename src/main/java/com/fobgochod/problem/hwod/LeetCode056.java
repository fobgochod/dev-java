package com.fobgochod.problem.hwod;

import java.util.*;

/**
 * 题目
 * LeetCode 56. 合并区间
 * 例1
 * 输入
 * 8
 * 1 9
 * 2 5
 * 19 20
 * 10 11
 * 12 20
 * 0 3
 * 0 1
 * 0 2
 * 输出
 * 0 9
 * 10 11
 * 12 20
 * <p>
 * 示例2
 * 输入
 * 4
 * 1 3
 * 2 6
 * 8 10
 * 15 18
 * 输出
 * 1 6
 * 8 10
 * 15 18
 */
public class LeetCode056 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        int[][] intervals = new int[num][2];
        for (int i = 0; i < num; i++) {
            intervals[i][0] = scanner.nextInt();
            intervals[i][1] = scanner.nextInt();
        }

        // 排序，按照左边第一位升序
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                return interval1[0] - interval2[0];
            }
        });

        int[][] merge = merge(intervals);

        for (int i = 0; i < merge.length; i++) {
            int[] ints = merge[i];
            for (int j = 0; j < merge[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] merge(int[][] intervals) {
        List<int[]> merged = new ArrayList<>();
        for (int i = 0; i < intervals.length; ++i) {
            int L = intervals[i][0], R = intervals[i][1];
            if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < L) {
                merged.add(new int[]{L, R});
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], R);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    public static int[][] merge2(int[][] intervals) {
        List<int[]> merged = new ArrayList<>();

        int start = 0;
        int end = 0;
        for (int i = 0; i < intervals.length; i++) {
            int[] row = intervals[i];

            if (i == 0) {
                start = row[0];
                end = row[1];
            }

            if (row[0] > start && row[1] > end && end >= row[0]) {
                end = row[1];
            }

            if (end < row[0]) {
                merged.add(new int[]{start, end});
                start = row[0];
                end = row[1];
            }
        }

        if (start > 0) {
            merged.add(new int[]{start, end});
        }
        return merged.toArray(new int[merged.size()][]);
    }
}
