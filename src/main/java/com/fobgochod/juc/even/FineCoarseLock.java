package com.fobgochod.juc.even;

public class FineCoarseLock {
    //等同于在方法的代码执行时要synchronized(this)
    public synchronized void lockThis() {
        System.out.println("FineCoarseLock = " + Thread.currentThread().getName());
    }

    //这里等同于synchronized(FineCoarseLock.class)
    public synchronized static void lockStatic() {
        System.out.println("FineCoarseLock = " + Thread.currentThread().getName());
    }
}
