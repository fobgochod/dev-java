package com.fobgochod.code.middle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Code107 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        double num = Double.parseDouble(br.readLine());
        double dichotomy = dichotomy(num);
        System.out.printf("%.1f", dichotomy);
    }


    private static double dichotomy(double num) {
        double mid = 0;
        double left = Math.min(-1.0, num);
        double right = Math.max(1.0, num);

        while (right - left > 0.001) {
            mid = (right + left) / 2;
            if (mid * mid * mid > num) {
                right = mid;
            } else if (mid * mid * mid < num) {
                left = mid;
            } else {
                return mid;
            }
        }
        return right;
    }
}
