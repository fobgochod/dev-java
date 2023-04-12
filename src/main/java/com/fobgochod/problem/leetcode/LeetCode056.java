package com.fobgochod.problem.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class LeetCode056 {

    public static void main(String[] args) {
        int[][] intervals = new int[8][2];

        intervals[0][0] = 1;
        intervals[0][1] = 9;

        intervals[1][0] = 2;
        intervals[1][1] = 5;

        intervals[2][0] = 19;
        intervals[2][1] = 20;

        intervals[3][0] = 10;
        intervals[3][1] = 11;

        intervals[4][0] = 12;
        intervals[4][1] = 20;

        intervals[5][0] = 0;
        intervals[5][1] = 3;

        intervals[6][0] = 0;
        intervals[6][1] = 1;

        intervals[7][0] = 0;
        intervals[7][1] = 2;

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
        if (intervals.length == 0) {
            return new int[0][2];
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                if (interval1[0] != interval2[0]) {
                    return interval1[0] - interval2[0];
                }
                return interval1[1] - interval2[1];
            }
        });


        List<int[]> merged = new ArrayList<int[]>();
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
}
