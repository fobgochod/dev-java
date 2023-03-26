package com.fobgochod.code.code2023;

import java.util.Scanner;

public class Code003 {

    /**
     * hello123world
     * hello123abc4
     *
     * hello123
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();
        String result = "";
        int start = 0;
        int end = 1;
        while (end <= str2.length()) {
            String subStr = str2.substring(start, end);
            if (str1.contains(subStr)) {
                result = subStr;
            } else {
                start++;
            }
            end++;
        }
        System.out.println(result);
    }
}
