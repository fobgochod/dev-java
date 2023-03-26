package com.fobgochod.problem.nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Code29 {

    static String l = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static String r = "BCDEFGHIJKLMNOPQRSTUVWXYZAbcdefghijklmnopqrstuvwxyza1234567890";

    // a-z 97-122  A-Z 65-90  0-9 48-57
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s1 = br.readLine();
        String s2 = br.readLine();


        for (char c : s1.toCharArray()) {
            char c1 = r.charAt(l.indexOf(c));
            System.out.print(c1);
        }
        System.out.println();
        for (char c : s2.toCharArray()) {
            char c1 = l.charAt(r.indexOf(c));
            System.out.print(c1);
        }
    }
}
