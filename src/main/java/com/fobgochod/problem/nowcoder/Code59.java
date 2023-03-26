package com.fobgochod.problem.nowcoder;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Code59 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        String[] strings = str.split("");
        Map<String, Long> map = Arrays.asList(strings).stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        boolean has = false;
        for (String s : strings) {
            if (map.get(s) == 1) {
                System.out.println(s);
                has = true;
                break;
            }
        }
        if (!has) {
            System.out.println("-1");
        }
    }
}
