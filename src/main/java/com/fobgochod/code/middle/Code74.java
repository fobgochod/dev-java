package com.fobgochod.code.middle;

import java.util.Scanner;

public class Code74 {

    // xcopy /s "C:\\program files" "d:\"
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        StringBuilder builder = new StringBuilder();
        int count = 1;
        boolean flag = false;
        for (char c : str.toCharArray()) {
            if (c == '\"') {
                flag = flag ? false : true;
                continue;
            }

            if (c != ' ') {
                builder.append(c);
            }
            if (c == ' ' && !flag) {
                builder.append("\n");
                count++;
            }
            if (c == ' ' && flag) {
                builder.append(c);
            }
        }
        System.out.println(count + "\n" + builder);
    }


}

