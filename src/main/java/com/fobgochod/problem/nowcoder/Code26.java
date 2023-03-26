package com.fobgochod.problem.nowcoder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Code26 {

    public static void main(String[] args) {
        //12.13
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        List<Character> letters = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            if (Character.isLetter(ch)) {
                letters.add(ch);
            }
        }
        letters.sort(Comparator.comparingInt(Character::toLowerCase));


        for (int i = 0, j = 0; i < str.toCharArray().length; i++) {
            if (Character.isLetter(str.charAt(i))) {
                System.out.print(letters.get(j++));
            } else {
                System.out.print(str.charAt(i));
            }
        }
    }
}
