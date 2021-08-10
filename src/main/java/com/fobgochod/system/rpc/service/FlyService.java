package com.fobgochod.system.rpc.service;

/**
 * FlyService.java
 *
 * @author Xiao
 * @date 2021/8/9 15:33
 */
public class FlyService implements Fly {

    @Override
    public void doFly(String msg) {
        System.out.println("do fly invoke, client arg:" + msg);
    }
}
