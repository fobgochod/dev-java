package com.fobgochod.java.snowflake;

/**
 * 功能描述
 *
 * @author seven
 * @date 2019/5/27
 */
public class MainTest {

    public static final int MILLION = 4096;

    public static void main(String[] args) throws InterruptedException {

        long dataCenterId = NodeUtil.getDataCenterId(5);
        long workerId = NodeUtil.getMaxWorkerId(dataCenterId, 5);
        System.out.println("dataCenterId = " + dataCenterId + "; workerId = " + workerId);

        SnowFlake snowFlake = new SnowFlake(dataCenterId, workerId);

        long start = System.currentTimeMillis();
        for (int i = 0; i < MILLION; i++) {
            System.out.println(snowFlake.nextId());
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
