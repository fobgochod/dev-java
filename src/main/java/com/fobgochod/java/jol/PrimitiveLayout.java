package com.fobgochod.java.jol;

import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteOrder;

public class PrimitiveLayout {

    private byte aByte; // 1字节
    private short aShort; // 2字节
    private int aInt; // 4字节
    private long aLong; // 8字节
    private float aFloat; // 4字节
    private double aDouble; // 8字节
    private boolean aBoolean; // 1字节
    private char aChar; // 2字节

    // 8字节  开启-XX:+UseCompressedOops，4字节
    private String aString;
    private byte[] bytes;
    private short[] shorts;
    private int[] ints;
    private long[] longs;
    private float[] floats;
    private double[] doubles;
    private boolean[] booleans;
    private char[] chars;
    private String[] strings;

    public static void main(String[] args) throws RunnerException {
        Object object = new Object();
        //打印hashcode
        System.out.println(object.hashCode());
        //查看字节序
        System.out.println(ByteOrder.nativeOrder());
        //打印当前jvm信息
        System.out.println(VM.current().details());

        System.out.println(ClassLayout.parseInstance(object).toPrintable());
        System.out.println(GraphLayout.parseInstance(object).toPrintable());

        PrimitiveLayout primitive = new PrimitiveLayout();
        System.out.println(ClassLayout.parseInstance(primitive).toPrintable());
        System.out.println(GraphLayout.parseInstance(primitive).toPrintable());
    }
}
