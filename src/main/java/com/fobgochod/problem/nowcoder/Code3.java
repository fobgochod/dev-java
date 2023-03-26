package com.fobgochod.problem.nowcoder;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Code3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Set<Integer> list = new HashSet<Integer>();
        sc.nextInt();
        while (sc.hasNextInt()) {
            list.add(sc.nextInt());
        }
        list.stream().sorted().forEach(System.out::println);
    }
}
