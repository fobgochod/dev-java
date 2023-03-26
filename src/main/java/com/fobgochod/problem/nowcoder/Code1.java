package com.fobgochod.problem.nowcoder;

import java.util.Scanner;

public class Code1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int offset = str.lastIndexOf(" ");
        int length = str.length() - offset - 1;
        System.out.println(length);
    }
}
