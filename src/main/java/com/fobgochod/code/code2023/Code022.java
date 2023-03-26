package com.fobgochod.code.code2023;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Code022 {

    /**
     * 5 -2 4 C D 9 + +
     * <p>
     * 27
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] nums = s.split(" ");
        List<Integer> list = new ArrayList<>();
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i].equals("+")) {
                int j = list.size();
                if (j < 2) {
                    System.out.println(-1);
                    return;
                }
                int grade = list.get(j - 1) + list.get(j - 2);
                list.add(grade);
            } else if (nums[i].equals("D")) {
                int j = list.size();
                if (j < 1) {
                    System.out.println(-1);
                    return;
                }
                // 上一个数字
                int last = list.get(j - 1) * 2;
                list.add(last);
            } else if (nums[i].equals("C")) {
                int j = i - 1;
                if (list.size() < 1) {
                    System.out.println(-1);
                    return;
                }
                list.remove(list.size() - 1);
            } else {
                list.add(new Integer(nums[i]));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            res += list.get(i);
        }
        System.out.println(res);

    }
}
