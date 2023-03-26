package com.fobgochod.problem.od2023;

import java.util.Scanner;

public class Code017 {

    /**
     * 4 4
     * 1 1 0 0
     * 0 0 0 1
     * 0 0 1 1
     * 1 1 1 1
     * <p>
     * 2
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int column = scanner.nextInt();
        int[][] numbers = new int[row][column];
        scanner.nextLine();
        for (int i = 0; i < row; i++) {
            String line = scanner.nextLine();
            String[] strs = line.split(" +");
            for (int j = 0; j < column; j++) {
                numbers[i][j] = Integer.parseInt(strs[j]);
            }
        }

        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (numbers[i][j] == 1) {
                    turnZero(numbers, row, column, i, j);
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    private static void turnZero(int[][] numbers, int row, int col, int x, int y) {
        numbers[x][y] = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < row && j >= 0 && j < col &&
                        numbers[i][j] == 1) {
                    turnZero(numbers, row, col, i, j);
                }
            }
        }
    }
}
