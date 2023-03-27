package com.fobgochod.problem.od2023;

import java.util.Scanner;

/**
 * 题目
 * 获取最大软件版本号
 * 例1
 * 输入
 * 1.3.11-S2
 * 1.3.11-S13
 * 输出
 * 1.3.11-S2
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class Code018 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        // 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
        String a = sc.nextLine();
        String b = sc.nextLine();

        String[] splitA = a.split("\\.");
        String[] splitB = b.split("\\.");

        //先比较主次版本
        for (int i = 0; i < 2; i++) {
            int firstOneA = Integer.valueOf(splitA[i]);
            int firstOneB = Integer.valueOf(splitB[i]);
            if (firstOneA != firstOneB) {
                System.out.println(firstOneA > firstOneB ? a : b);
                return;
            }
        }

        //最后增量版本-里程碑版本
        if (splitA.length > 2 && splitB.length > 2) {
            //有增量版本
            String[] splitA2 = splitA[2].split("-");
            String[] splitB2 = splitB[2].split("-");
            int firstOneA = Integer.valueOf(splitA2[0]);
            int firstOneB = Integer.valueOf(splitB2[0]);
            //比较增量版本
            if (firstOneA != firstOneB) {
                System.out.println(firstOneA > firstOneB ? a : b);
                return;
            }
            if (splitA2.length == 2 && splitB2.length == 2) {
                //有里程碑版本
                System.out.println(splitA2[1].compareTo(splitB2[1]) >= 0 ? a : b);
            } else {
                System.out.println(splitA2.length >= splitB2.length ? a : b);
            }
        } else {
            System.out.println(splitA.length >= splitB.length ? a : b);
        }

    }
}
