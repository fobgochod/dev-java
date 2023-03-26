package com.fobgochod.problem.nowcoder;

import java.util.Scanner;

public class Code90 {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] elements = sc.nextLine().split("\\.");

        if (elements.length != 4) {
            System.out.println("NO");
            return;
        }

        for (String element : elements) {
            if (element.length() == 0 || element.length() > 3) {
                System.out.println("NO");
                return;
            }
            int dig = 0;
            try {
                dig = Integer.parseInt(element);
            } catch (NumberFormatException e) {
                System.out.println("NO");
                return;
            }

            if (dig > 255 || dig < 0) {
                System.out.println("NO");
                return;
            }

            if (String.valueOf(dig).length() != element.length()) {
                System.out.println("NO");
                return;
            }

        }
        System.out.println("YES");
    }
}
