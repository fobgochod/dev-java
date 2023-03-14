package com.fobgochod.collection;

import java.util.ArrayList;
import java.util.List;

public class ListSample {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(0);
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        ArrayList<Integer> list1 = new ArrayList<>();
        final int N = 10000000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list1.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法前：" + (endTime - startTime));

        ArrayList<Object> list2 = new ArrayList<>();
        long startTime1 = System.currentTimeMillis();
        list2.ensureCapacity(N);
        for (int i = 0; i < N; i++) {
            list2.add(i);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法后：" + (endTime1 - startTime1));
    }
}
