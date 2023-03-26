package com.fobgochod.code.middle;

import java.util.Scanner;

public class Code38 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double high = scanner.nextDouble();

        int count = 0;
        double sum = 0;
        while (count < 5) {
            // 第一次落地
            sum += high;
            // 回升高度 1/2
            high = high / 2;
            // +回去的高度
            if (count < 4) {
                sum += high;
            }

            count++;
        }
        System.out.println(sum);
        System.out.println(high);
    }
}
