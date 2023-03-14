package com.fobgochod.juc.even.auth;

import com.fobgochod.juc.even.auth.p.C;
import com.fobgochod.juc.even.auth.p.D;

/**
 * @auther: Xiao
 * @date: 2020/8/1 22:42
 * @description: 功能描述
 */
public class A {

    private static int x1 = 1;
    static int x2 = 2;
    protected static int x3 = 3;
    public static int x4 = 4;


    public static void main(String[] args) {
        B.main(new String[]{});
        C.main(new String[]{});
        D.main(new String[]{});
    }
}
