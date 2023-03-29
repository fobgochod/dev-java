package com.fobgochod.problem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 0  9  A  Z  a  z
 * 48 57 65 90 97 122
 */
public class CodeTools {

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

        // 按字母分组
        Map<String, Long> letMap = Arrays.stream(letters).collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        // 是否字母
        boolean la = Character.isLetter('a');
        System.out.println("a is letter." + la);
        // 是否数字
        boolean l1 = Character.isDigit('1');
        System.out.println("1 is digit." + l1);

    }
}
