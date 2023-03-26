package com.fobgochod.java.juc.even;

public class DCLSingle {

    private int a = 1;
    private static volatile DCLSingle instance;

    private DCLSingle() {
    }

    public static DCLSingle getInstance() {
        if (instance == null) {
            synchronized (DCLSingle.class) {
                if (instance == null) {
                    instance = new DCLSingle();
                }
            }
        }
        return instance;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(DCLSingle.getInstance().hashCode());
            }).start();
        }
    }
}
