package com.fobgochod.problem.nowcoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Code105 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int minus = 0;
        List<Integer> plusList = new ArrayList<>();
        while (sc.hasNextInt()) {
            int anInt = sc.nextInt();
            if (anInt < 0) {
                minus++;
            } else {
                plusList.add(anInt);
            }
        }
        System.out.println(minus);
        if (plusList.isEmpty()) {
            System.out.print("0.0");
        } else {
            int sum = plusList.stream().mapToInt(Integer::intValue).sum();
            System.out.printf("%.1f", sum * 1.0 / plusList.size());
        }
    }

}
