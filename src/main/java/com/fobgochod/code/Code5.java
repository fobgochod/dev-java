package com.fobgochod.code;

import java.util.Scanner;

public class Code5 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            System.out.println(Integer.parseInt(s.substring(2), 16));
        }
    }
}
