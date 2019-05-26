package com.fobgochod;

/**
 * 功能描述
 *
 * @author seven
 * @date 2019/5/27
 */
public class MainTest {

    public static final int MILLION = 1000000;

    public static void main(String[] args) {

        SnowFlake snowFlake = new SnowFlake(3, 2);

        long start = System.currentTimeMillis();
        for (int i = 0; i < MILLION; i++) {
            System.out.println(snowFlake.nextId());
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
