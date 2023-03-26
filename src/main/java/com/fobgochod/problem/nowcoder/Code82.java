package com.fobgochod.problem.nowcoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 8/11  1/2+ 5/22  1/5+ 3/110   1/55 1/110
 * <p>
 * <p>
 * 先将 a/b 转换成 1/x+y/z 的形式
 * y/z 使用递归继续分解，直到分子为1
 * <p>
 * b除以a，得商x，得余数y   b=a*x+y
 */
public class Code82 {

    // 17/73 = 1/5+1/31+1/1617+1/6098785+1/18296355
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = bf.readLine()) != null) {
            String[] input = str.split("/");
            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);

            if (a > b || a < 1 || b < 2) break;


            while (a != 1) {
                if (b % a == 0) {
                    b = b / a;
                    a = 1;
                    continue;
                }
                if (b % (a - 1) == 0) {
                    int c = b / (a - 1);
                    a = 1;
                    System.out.print("1/" + c + "+");
                } else {
                    int c = b / a + 1;
                    a = a - b % a;
                    b = b * c;
                    System.out.print("1/" + c + "+");
                }
            }
            System.out.println("1/" + b);


        }
        bf.close();
    }

}
