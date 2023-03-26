package com.fobgochod.code.middle;

import java.util.*;

public class Code41 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();

        int[] weights = new int[count];
        int[] nums = new int[count];

        for (int i = 0; i < count; i++) {
            weights[i] = scanner.nextInt();
        }

        for (int i = 0; i < count; i++) {
            nums[i] = scanner.nextInt();
        }

        Set<Integer> result = new HashSet<>();
        result.add(0);
        for (int i = 0; i < count; i++) {// 遍历砝码
            List<Integer> lists = new ArrayList<>(result); //取出当前所有结果
            for (int j = 1; j <= nums[i]; j++) { //遍历个数
                for (Integer sw : lists) {
                    result.add(sw + weights[i] * j);
                }
            }
        }
        System.out.println(result.size());
    }
}
