package com.fobgochod.problem.nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Code17 {

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] options = bf.readLine().split(";");

        int x = 0;
        int y = 0;
        for (String option : options) {
            if (!option.matches("[WASD][0-9]{1,2}")) {
                continue;
            }
            char type = option.charAt(0);
            int num = Integer.parseInt(option.substring(1));
            switch (type) {
                case 'A':
                    x = x - num;
                    break;
                case 'D':
                    x = x + num;
                    break;
                case 'S':
                    y = y - num;
                    break;
                case 'W':
                    y = y + num;
                    break;
            }
        }

        System.out.println(x + "," + y);
    }
}
