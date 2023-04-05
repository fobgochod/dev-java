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


        int size = tableSizeFor(18);
        System.out.println("size = " + size);
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
