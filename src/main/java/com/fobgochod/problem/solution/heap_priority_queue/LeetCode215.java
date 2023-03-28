package com.fobgochod.problem.solution.heap_priority_queue;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * 数组中的第K个最大元素
 * 例1
 * 输入
 * 3,2,3,1,2,4,5,5,6
 * 4
 * 输出
 * 4
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class LeetCode215 {

    Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] digits = scanner.nextLine().split(",");
        int[] ints = Arrays.stream(digits).mapToInt(Integer::valueOf).toArray();

        int kthLargest = new LeetCode215().findKthLargest(ints, scanner.nextInt());
        System.out.println(kthLargest);
    }

    public int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public int quickSelect(int[] a, int l, int r, int index) {
        int q = randomPartition(a, l, r);
        if (q == index) {
            return a[q];
        } else {
            return q < index ? quickSelect(a, q + 1, r, index) : quickSelect(a, l, q - 1, index);
        }
    }

    public int randomPartition(int[] a, int l, int r) {
        int i = random.nextInt(r - l + 1) + l;
        swap(a, i, r);
        return partition(a, l, r);
    }

    public int partition(int[] a, int l, int r) {
        int x = a[r], i = l - 1;
        for (int j = l; j < r; ++j) {
            if (a[j] <= x) {
                swap(a, ++i, j);
            }
        }
        swap(a, i + 1, r);
        return i + 1;
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
