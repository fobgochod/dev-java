package com.fobgochod.problem.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，
 * 并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
 * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出：[[1,6],[8,10],[15,18]]
 * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * 输入：intervals = [[1,4],[4,5]]
 * 输出：[[1,5]]
 * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
 *
 * @author fobgochod
 * @date 2023/4/12 8:58
 */
public class LeetCode56 {


    public static void main(String[] args) {

        List<Dom> input = new LinkedList<>();
        input.add(new Dom(0, 3));
        input.add(new Dom(1, 9));
        input.add(new Dom(2, 5));
        input.add(new Dom(10, 11));
        input.add(new Dom(12, 20));
        input.add(new Dom(19, 20));

        List<Dom> output = new LinkedList<>();

        int start = 0;
        int end = 0;

        int count = 0;
        for (Dom dom : input) {
            if (count == 0) {
                start = dom.getStart();
                end = dom.getEnd();
            }
            count++;

            if (dom.getStart() > start && dom.getEnd() > end && end >= dom.getStart()) {
                end = dom.getEnd();
            }
            if (end < dom.getStart()) {
                output.add(new Dom(start, end));
                start = dom.getStart();
                end = dom.getEnd();
            }

        }

        if (start > 0) {
            output.add(new Dom(start, end));
        }

        for (Dom dom : output) {
            System.out.println(dom.getStart() + "," + dom.getEnd());
        }
    }


    private static class Dom {
        private int start;
        private int end;

        public Dom(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }
}
