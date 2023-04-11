package com.fobgochod.problem.hwod;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 题目
 * 最优芯片资源占用
 * 某块业务芯片最小容量单位为1.25G，总容量为M*1.25G，对该芯片资源编号为1.2....M。该芯片支持3种不同的配置，分别为A、B、C。
 * 配置A:占用容量为 1.25*1=1.25G
 * 配置B:占用容量为1.25*2=2.5G
 * 配置C:占用容量为 1.25*8=10G
 * <p>
 * 输入描述
 * 第一行一个整数M：每块芯片容量为 M*1.25G，取值范围为:1~256
 * 第二行一个整数N：板卡包含的芯片数量，取值范围为1~32
 * 第三行为一个字符串，表示用户配置序列:例如ACABA，长度不超过1000
 * <p>
 * 输出描述
 * 板卡上每块芯片的占用情况
 * 例1
 * 输入
 * 8
 * 2
 * ACABA
 * 输出
 * 11111000
 * 11111111
 * <p>
 * 示例2
 * 输入
 * 8
 * 2
 * ACBCB
 * 输出
 * 11111000
 * 11111111
 */
public class CodeOD3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int col = scanner.nextInt();
        int row = scanner.nextInt();
        char[] chips = scanner.next().toCharArray();

        int[] chipSpaces = new int[chips.length];
        IntStream.range(0, chips.length).forEach(i -> {
            if (chips[i] == 'A') {
                chipSpaces[i] = 1;
            } else if (chips[i] == 'B') {
                chipSpaces[i] = 2;
            } else if (chips[i] == 'C') {
                chipSpaces[i] = 8;
            }
        });

        int[][] result = new int[row][col];

        for (int chip : chipSpaces) {
            process(result, row, col, chip);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(result[i][j]);
            }
            System.out.println();
        }
    }

    private static void process(int[][] result, int row, long col, int chip) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (result[i][j] == 0 && col - j >= chip) {
                    for (int k = 0; k < chip; k++) {
                        result[i][j + k] = 1;
                    }
                    return;
                }
            }
        }
    }
}
