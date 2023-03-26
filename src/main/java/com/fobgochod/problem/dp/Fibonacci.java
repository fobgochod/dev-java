package com.fobgochod.problem.dp;

public class Fibonacci {

    public static void main(String[] args) {
        int col = 6;
        System.out.println("col = " + calc(col));
    }

    private static int calc(int col) {
        if (col == 1 || col == 2) {
            return 1;
        }
        return calc(col - 1) + calc(col - 2);
    }
}
