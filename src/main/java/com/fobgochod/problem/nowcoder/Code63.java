package com.fobgochod.problem.nowcoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Code63 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int num = scanner.nextInt();

        int maxIndex = -1;
        int maxGc = 0;
        List<String> child = new ArrayList<>();
        for (int i = 0; i < str.length() - num + 1; i++) {
            String substr = str.substring(i, i + num);
            child.add(substr);
            int count = getCount(substr);
            if (maxGc < count) {
                maxGc = count;
                maxIndex = i;
            }
        }
        if (maxIndex > -1) {
            System.out.println(child.get(maxIndex));
        }
    }

    private static int getCount(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'G' || str.charAt(i) == 'C') {
                count++;
            }
        }
        return count;
    }
}
