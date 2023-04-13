package com.fobgochod.problem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * 题目
 * 例1
 * 输入
 * 输出
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class CodeSample {

    public static void main(String[] args) {
        String str10 = new StringBuffer("123456789").reverse().toString();
        System.out.println("str10 = " + str10);

        String str15 = new StringBuilder("987654321").reverse().toString();
        System.out.println("str15 = " + str15);

        //一维数组排序
        Integer[] arr1 = {1, 9, 8, 2, 5};
        Arrays.sort(arr1);
        for (int i : arr1) {
            System.out.print(i + " ");
        }
        System.out.println();

        Arrays.sort(arr1, new DescComparator());
        for (int i : arr1) {
            System.out.print(i + " ");
        }
        System.out.println();

        // 二维数组排序
        int[][] arr2 = new int[10][2];
        for (int[] ints : arr2) {
            ints[0] = new Random().nextInt(10);
            ints[1] = new Random().nextInt(10);
        }

        Arrays.sort(arr2, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });

        for (int[] ints : arr2) {
            for (int j = 0; j < arr2[0].length; j++) {
                System.out.print(ints[j]);
            }
            System.out.println();
        }
    }


    static class DescComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            //如果n1小于n2，我们就返回正值，如果n1大于n2我们就返回负值，
            //这样颠倒一下，就可以实现反向排序了
            return o2 - o1;
        }
    }
}
