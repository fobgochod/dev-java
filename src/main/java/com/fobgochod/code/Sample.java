package com.fobgochod.code;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Sample {

    public static void main(String[] args) {
        String str = "efghabcda";
        String[] letters = str.split("");
        char[] chars = str.toCharArray();

        // 升序
        Arrays.sort(chars);
        System.out.println(chars);
        // 降序
        Arrays.sort(letters, Comparator.reverseOrder());
        System.out.println(String.join("", letters));

        Map<String, Long> map = Arrays.stream(letters).collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        boolean la = Character.isLetter('a');
        System.out.println("a is letter." + la);
        boolean l1 = Character.isDigit('1');
        System.out.println("1 is digit." + l1);

    }
}
