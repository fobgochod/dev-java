package com.fobgochod.spring.ioc.methodinjection.replaced;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

public class ReplacedPojoMethodReplacer implements MethodReplacer {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("Hello, I'm replaced method with parameter: " + args[0]);
        return obj;
    }
}
