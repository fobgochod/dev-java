package com.fobgochod.code.middle;

import java.util.Scanner;

public class Code33 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String s = sc.next();
            if (s.contains(".")) {
                System.out.println(ip2num(s));
            } else {
                System.out.println(num2ip(Long.parseLong(s)));
            }
        }
    }

    public static long ip2num(String ip) {
        String[] iip = ip.split("\\.");
        long ans = 0;
        for (int i = 0; i < 4; i++) {
            ans = ans * 256 + Long.parseLong(iip[i]);
        }
        return ans;
    }

    public static String num2ip(long num) {
        String[] ans = new String[4];
        for (int i = 3; i >= 0; i--) {
            ans[i] = Long.toString(num % 256);
            num = num / 256;
        }
        return String.join(".", ans);
    }
}
