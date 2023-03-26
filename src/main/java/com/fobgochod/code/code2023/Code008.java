package com.fobgochod.code.code2023;

import java.util.Arrays;
import java.util.Scanner;

public class Code008 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String newsapper = sc.nextLine();
        String anonymousLetter = sc.nextLine();
        String[] newsapperArr = newsapper.split(" ");
        String[] anonymousLetterArr = anonymousLetter.split(" ");
        for (int i = 0; i < anonymousLetterArr.length; i++) {
            String ii = anonymousLetterArr[i];
            char[] ic = ii.toCharArray();
            Arrays.sort(ic);
            boolean flag = false;
            for (int j = 0; j < newsapperArr.length; j++) {
                String jj = newsapperArr[j];
                char[] jc = jj.toCharArray();
                Arrays.sort(jc);
                if (Arrays.equals(ic, jc)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.out.println(false);
                System.exit(0);
            }
        }
        System.out.println(true);
    }
}
