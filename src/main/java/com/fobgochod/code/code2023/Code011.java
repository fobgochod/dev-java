package com.fobgochod.code.code2023;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Code011 {

    /**
     * AAAA
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        Zouwei z = new Zouwei(str);
        int perfect = str.length() / 4;
        Map<Character, Integer> offset = new HashMap<>();
        offset.put('W', z.W - perfect);
        offset.put('A', z.A - perfect);
        offset.put('S', z.S - perfect);
        offset.put('D', z.D - perfect);

        int min = str.length();
        for (int i = 0; i < str.length(); i++) {
            HashMap<Character, Integer> copy = new HashMap<>(offset);
            int len = 0;
            if (copy.get(str.charAt(i)) > 0) {
                for (int j = i; j < str.length(); j++) {
                    char c = str.charAt(j);
                    copy.put(c, copy.get(c) - 1);
                    len++;
                    if (allClear(copy)) {
                        break;
                    }
                }
            }
            if (allClear(copy)) {
                min = Math.min(min, len);
            }
        }

        System.out.println(min);

    }

    private static boolean allClear(HashMap<Character, Integer> copy) {
        Collection<Integer> values = copy.values();
        for (Integer value : values) {
            if (value > 0) {
                return false;
            }
        }
        return true;
    }

    static class Zouwei {
        Integer W = 0;
        Integer A = 0;
        Integer S = 0;
        Integer D = 0;
        String code;

        private Zouwei() {

        }

        public Zouwei(String code) {
            this.code = code;
            for (char c : code.toCharArray()) {
                switch (c) {
                    case 'W':
                        this.W++;
                        break;
                    case 'A':
                        this.A++;
                        break;
                    case 'S':
                        this.S++;
                        break;
                    case 'D':
                        this.D++;
                        break;
                }
            }
        }
    }
}
