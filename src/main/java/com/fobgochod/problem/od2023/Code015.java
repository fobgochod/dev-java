package com.fobgochod.problem.od2023;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 通信误码
 * 例1
 * 输入
 * 5
 * 1 2 2 4 1
 * 输出
 * 2
 * <p>
 * 示例2
 * 输入
 * 7
 * 1 2 2 4 2 1 1
 * 输出
 * 4
 */
public class Code015 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();

        int[] codes = new int[count];
        for (int i = 0; i < count; i++) {
            codes[i] = scanner.nextInt();
        }
        Map<String, Long> map = Arrays.stream(codes).mapToObj(String::valueOf).collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        long max = map.values().stream().max(Comparator.naturalOrder()).get();
        List<Integer> maxIds = map.entrySet().stream()
                .filter(e -> e.getValue() == max)
                .map(Map.Entry::getKey)
                .map(Integer::valueOf)
                .toList();


        int min = Integer.MAX_VALUE;
        for (Integer maxId : maxIds) {
            int start = -1, end = -1;
            for (int j = 0; j < codes.length; j++) {
                if (codes[j] == maxId) {
                    if (start == -1) {
                        start = j;
                    } else {
                        end = j;
                    }
                }
            }
            min = Math.min(min, end - start + 1);
        }

        System.out.println(min);
    }
}
