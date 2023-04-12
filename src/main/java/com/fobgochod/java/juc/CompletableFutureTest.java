package com.fobgochod.java.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class CompletableFutureTest {

    private static volatile int sum = 0;

    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calc(1, 1, 1))
                .whenCompleteAsync((integer, throwable) -> sum += integer);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> calc(1, 1, 1))
                .whenCompleteAsync((integer, throwable) -> sum += integer);
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> calc(1, 1, 1))
                .whenCompleteAsync((integer, throwable) -> sum += integer);

        CompletableFuture.allOf(future, future2, future3).join();


        System.out.println("sum = " + sum);

    }


    private static int calc(int a, int b, long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException ignored) {
        }
        return a + b;
    }
}
