package com.fobgochod.java.jol;

import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * JOL is the set of tools for examining field and object layouts in JVM.
 *
 * @author fobgochod
 * @date 2023/3/16 12:39
 */
public class ListLayout {

    public static void main(String[] args) throws RunnerException {
        Object[] objects = new Object[10];
        IntStream.range(0, 10).forEach(p -> objects[p] = p);
        System.out.println(ClassLayout.parseInstance(objects).toPrintable());

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        System.out.println("arrayList = " + ClassLayout.parseInstance(arrayList).toPrintable());
        System.out.println("linkedList = " + ClassLayout.parseInstance(linkedList).toPrintable());

        IntStream.range(0, 1).forEach(arrayList::add);
        IntStream.range(0, 1).forEach(linkedList::add);

        System.out.println("arrayList = " + GraphLayout.parseInstance(arrayList).toFootprint());
        System.out.println("linkedList = " + GraphLayout.parseInstance(linkedList).toFootprint());
    }
}
