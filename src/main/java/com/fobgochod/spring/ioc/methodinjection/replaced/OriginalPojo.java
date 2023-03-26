package com.fobgochod.spring.ioc.methodinjection.replaced;

public class OriginalPojo {

    public void hello() {
        System.out.println("Hello, I'm original method.");
    }

    public void hello(String name) {
        System.out.println("Hello, I'm original method with parameter: " + name);
    }
}
