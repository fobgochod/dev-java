package com.fobgochod.problem.solution.dynamic_programming;

public class Chorus {

    // 186 200 160 130
    // 150 200 160 130
    public static void main(String[] args) {
        int[] teams = {186, 186, 150, 200, 160, 130, 197, 200};

        int[] dp1 = new int[8];
        for (int i = 0; i < teams.length; i++) {
            int max = teams[i];
            dp1[i] = 1;
            for (int j = i; j < teams.length - 1; j++) {
                if (max < teams[j + 1]) {
                    max = teams[j + 1];
                    dp1[i] = dp1[i] + 1;
                }
            }
        }

        int[] dp2 = new int[8];
        for (int i = 0; i < teams.length; i++) {
            int min = teams[i];
            dp2[i] = 1;
            for (int j = i; j < teams.length - 1; j++) {
                if (min > teams[j + 1]) {
                    min = teams[j + 1];
                    dp2[i] = dp2[i] + 1;
                }
            }
        }

        System.out.println("dp1 = " + dp1);
    }
}
