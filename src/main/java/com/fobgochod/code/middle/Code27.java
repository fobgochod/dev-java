package com.fobgochod.code.middle;

import java.util.*;

public class Code27 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        List<String> dics = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            dics.add(sc.next());
        }
        String owner = sc.next();
        int order = sc.nextInt();

        List<String> brothers = new ArrayList<>();
        for (String dic : dics) {
            if (isBrother(dic, owner)) {
                brothers.add(dic);
            }
        }
        Collections.sort(brothers);

        System.out.println(brothers.size());

        if (brothers.size() >= order) {
            System.out.println(brothers.get(order - 1));
        }

    }

    private static boolean isBrother(String a, String owner) {
        if (a.length() != owner.length() || a.equals(owner)) {
            return false;
        }
        char[] aChars = a.toCharArray();
        char[] ownerChars = owner.toCharArray();

        Arrays.sort(aChars);
        Arrays.sort(ownerChars);
        return new String(aChars).equals(new String(ownerChars));
    }
}
