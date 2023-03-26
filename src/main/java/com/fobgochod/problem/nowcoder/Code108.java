package com.fobgochod.problem.nowcoder;

import java.util.Scanner;

public class Code108 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(" ");
        int a = Integer.parseInt(split[0]);
        int b = Integer.parseInt(split[1]);
        System.out.println(gcb(a, b, a));
    }

    private static int gcb(int a, int b, int c) {
        if (a % b == 0) {
            return a;
        }
        return gcb(a + c, b, c);
    }
}
