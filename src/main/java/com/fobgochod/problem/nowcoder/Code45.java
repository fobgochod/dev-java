package com.fobgochod.problem.nowcoder;

import java.util.*;
import java.util.stream.Collectors;

public class Code45 {

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        while (scan.hasNext()) {
            String input = scan.nextLine();
            Map<String, Long> map = Arrays.stream(input.split("")).collect(Collectors.groupingBy(p -> p, Collectors.counting()));
            int sum = 0;
            int happy = 26;
            List<Long> list = map.values().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            for (Long aLong : list) {
                sum += aLong * happy;
                happy--;
            }
            System.out.println(sum);
        }
    }
}
