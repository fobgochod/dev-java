package com.fobgochod.problem.hwod;

import java.util.Scanner;

/**
 * 题目
 * 对称字符串
 * 例1
 * 输入
 * 5
 * 1 0
 * 2 1
 * 3 2
 * 4 6
 * 5 8
 * 输出
 * red
 * red
 * blue
 * blue
 * blue
 * <p>
 * 示例2
 * 输入
 * 1
 * 64 73709551616
 * 输出
 * red
 */
public class CodeOD2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();

        for (int i = 0; i < count; i++) {
            int row = scanner.nextInt();
            long col = scanner.nextLong();
            char result = process(row, col);
            System.out.println(result == 'R' ? "red" : "blue");
        }
    }

    private static char process(int row, long col) {
        if (row == 1) {
            return 'R';
        }
        long preRowLen = (long) Math.pow(2, row - 2);
        if (col >= preRowLen) {
            return process(row - 1, col - preRowLen);
        }
        return reverse(process(row - 1, col));
    }

    private static char reverse(char target) {
        return target == 'R' ? 'B' : 'R';
    }
}
