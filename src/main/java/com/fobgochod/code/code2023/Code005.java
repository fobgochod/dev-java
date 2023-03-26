package com.fobgochod.code.code2023;

import java.util.Scanner;

public class Code005 {

    /**
     * woh era uoy ? I ma enif.
     * how are you ? I am fine.
     *
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String result = "";
        int start = 0;
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a == ' ' || a == '?' || a == '.' || a == ',') {
                if (i > start) {
                    StringBuilder word = new StringBuilder(str.substring(start, i));
                    result = result + word.reverse() + a;
                } else {
                    result = result + a;
                }
                start = i + 1;
            } else if (i == str.length() - 1) {
                StringBuilder word = new StringBuilder(str.substring(start, i + 1));
                result = result + word.reverse();
            }
        }
        System.out.println(result);
    }
}
