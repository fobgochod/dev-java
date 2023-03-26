package com.fobgochod.problem.nowcoder;

import java.util.Scanner;

public class Code106 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] split = str.split("");
        for (int i = split.length - 1; i >= 0; i--) {
            System.out.print(split[i]);
        }
    }
}
