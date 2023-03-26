package com.fobgochod.problem.od2023;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Code004 {

    /**
     * 4 4
     * X X X X
     * X O O X
     * X O O X
     * X O X X
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            int m = in.nextInt();
            int n = in.nextInt();
            in.nextLine();
            Character[][] ca = new Character[m][n];
            for (int i = 0; i < m; i++) {
                String[] sa = in.nextLine().split(" ");
                for (int j = 0; j < n; j++) {
                    ca[i][j] = sa[j].charAt(0);
                }
            }

            int maxCount = 0;
            HashMap<String, Integer> map = new HashMap<>();
            for (int j = 0; j < n; j++) {
                if (ca[0][j] == 'O') {
                    int count = calc(copy(ca), 0, j, true);
                    if (count > 0) {
                        String key = 0 + " " + j;
                        map.put(key, count);
                        if (count > maxCount) {
                            maxCount = count;
                        }
                    }
                }

                if (ca[m - 1][j] == 'O') {
                    int count2 = calc(copy(ca), m - 1, j, true);
                    if (count2 > 0) {
                        String key = (m - 1) + " " + j;
                        map.put(key, count2);
                        if (count2 > maxCount) {
                            maxCount = count2;
                        }
                    }
                }
            }

            for (int i = 1; i < m - 1; i++) {
                if (ca[i][0] == 'O') {
                    int count = calc(copy(ca), i, 0, true);
                    if (count > 0) {
                        String key = i + " " + 0;
                        map.put(key, count);
                        if (count > maxCount) {
                            maxCount = count;
                        }
                    }
                }

                if (ca[i][n - 1] == 'O') {
                    int count2 = calc(copy(ca), i, n - 1, true);
                    if (count2 > 0) {
                        String key = i + " " + (n - 1);
                        map.put(key, count2);
                        if (count2 > maxCount) {
                            maxCount = count2;
                        }
                    }
                }
            }

            String maxKey = "";
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                if (e.getValue() == maxCount) {
                    if (maxKey.isEmpty()) {
                        maxKey = e.getKey();
                    } else {
                        maxKey = "more";
                        break;
                    }
                }
            }

            if (maxCount == 0) {
                System.out.println("NULL");
            } else if (maxKey == "more") {
                System.out.println(maxCount);
            } else {
                System.out.println(maxKey + " " + maxCount);
            }
        }
    }

    public static Character[][] copy(Character[][] a) {
        int m = a.length;
        int n = a[0].length;
        Character[][] ca = new Character[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ca[i][j] = a[i][j];
            }
        }
        return ca;
    }

    public static int calc(Character[][] ca, int i, int j, boolean isRuKou) {
        if (!isRuKou) {
            if (i == 0 || i == ca.length - 1 || j == 0 || j == ca[0].length - 1) {
                return 0;
            }
        }

        ca[i][j] = 'X';
        int count = 1;

        if (j + 1 < ca[0].length && ca[i][j + 1] == 'O') {
            int count1 = calc(ca, i, j + 1, false);
            if (count1 == 0) {
                return 0;
            }
            count += count1;
        }

        if (i + 1 < ca.length && ca[i + 1][j] == 'O') {
            int count1 = calc(ca, i + 1, j, false);
            if (count1 == 0) {
                return 0;
            }
            count += count1;
        }

        if (j - 1 >= 0 && ca[i][j - 1] == 'O') {
            int count1 = calc(ca, i, j - 1, false);
            if (count1 == 0) {
                return 0;
            }
            count += count1;
        }

        if (i - 1 >= 0 && ca[i - 1][j] == 'O') {
            int count1 = calc(ca, i - 1, j, false);
            if (count1 == 0) {
                return 0;
            }
            count += count1;
        }

        return count;
    }
}
