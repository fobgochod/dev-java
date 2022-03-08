package com.fobgochod.jvm;

/**
 * @auther: Xiao
 * @date: 2021/11/23 23:35
 * @description: 功能描述
 */
public class TestHashCode {

    /**
     * -XX:+UnlockExperimentalVMOptions -XX:hashCode=2
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new Object());
        System.out.println(new Object());
        System.out.println(new Object());
        System.out.println(new Object());
    }
}
