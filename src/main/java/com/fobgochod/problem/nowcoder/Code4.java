package com.fobgochod.problem.nowcoder;

import java.util.Scanner;

public class Code4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] split = sc.nextLine().split("");
        int num = 0;
        for (String s : split) {
            System.out.print(s);
            num++;
            if (num == 8) {
                num = 0;
                System.out.println();
            }
        }
        if (num > 0) {
            for (int i = 0; i < 8 - num; i++) {
                System.out.print(0);
            }
        }
    }
}
