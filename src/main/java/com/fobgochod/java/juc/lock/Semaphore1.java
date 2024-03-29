package com.fobgochod.java.juc.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Semaphore1 {

    public static void main(String[] args) {

        int parkSpace = 3;
        System.out.println("这里有" + parkSpace + "个停车位,先到先得啊！");
        Semaphore semaphore = new Semaphore(parkSpace, true);

        Thread threadA = new Thread(new ParkCar(1, "布加迪", semaphore), "赵四");
        Thread threadB = new Thread(new ParkCar(2, "法拉利", semaphore), "刘能、谢广坤");
        Thread threadC = new Thread(new ParkCar(1, "劳斯莱斯", semaphore), "why哥");

        threadA.start();
        threadB.start();
        threadC.start();
    }

    static class ParkCar implements Runnable {

        private final int n;
        private final String carName;
        private final Semaphore semaphore;

        public ParkCar(int n, String carName, Semaphore semaphore) {
            this.n = n;
            this.carName = carName;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                if (semaphore.availablePermits() < n) {
                    System.out.println(Thread.currentThread().getName() + "来停车,但是停车位不够了,等着吧");
                }
                semaphore.acquire(n);
                System.out.println(Thread.currentThread().getName() + "把自己的" + carName + "停进来了,剩余停车位:" + semaphore.availablePermits() + "辆");
                //模拟停车时长
                int parkTime = ThreadLocalRandom.current().nextInt(1, 6);
                TimeUnit.SECONDS.sleep(parkTime);
                System.out.println(Thread.currentThread().getName() + "把自己的" + carName + "开走了,停了" + parkTime + "小时");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                semaphore.release(n);
                System.out.println(Thread.currentThread().getName() + "走后,剩余停车位:" + semaphore.availablePermits() + "辆");
            }
        }
    }
}
