package com.fobgochod.system.rpc.service;

import java.io.Serializable;

/**
 * Person.java
 *
 * @author Xiao
 * @date 2021/8/9 16:02
 */
public class Person implements Serializable {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
