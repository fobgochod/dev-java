package com.fobgochod.java.collection;

import java.util.HashMap;
import java.util.Map;

public class MapSample {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            map.put(i, i);
        }
        map.put(16, 16);
        System.out.println("map = " + map);
    }
}
