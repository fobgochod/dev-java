package com.fobgochod.system.rpc.service;

/**
 * CarService.java
 *
 * @author fobgochod
 * @date 2021/8/9 15:33
 */
public class CarService implements Car {

    @Override
    public String doCar(Integer msg) {
        // System.out.println("do car invoke, client arg:" + msg);
        return String.valueOf(msg + 100);
    }

    @Override
    public Person getMan(String name, Integer age) {
        Person p = new Person();
        p.setName(name);
        p.setAge(age);
        return p;
    }
}
