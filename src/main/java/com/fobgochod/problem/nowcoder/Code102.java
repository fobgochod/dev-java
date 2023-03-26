package com.fobgochod.problem.nowcoder;

import java.util.HashMap;
import java.util.Map;

public class Code102 {

    public static void main(String[] args) {
        String str = "aaddccdc";

        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        map.entrySet().stream().sorted((o1, o2) -> {
            if (o1.getValue() > o2.getValue()) {
                return -1;
            } else if (o1.getValue() < o2.getValue()) {
                return 1;
            }
            return o1.getKey() - o2.getKey();
        }).forEach(p -> System.out.print(p.getKey()));
    }
}
