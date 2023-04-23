package com.fobgochod.spring.aop.adapter.annotation.summer;

import java.lang.reflect.Method;

public class AspectJSummerAdvice implements SummerAdvice {

    private int invocationCounter;

    @Override
    public void summer(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("Summer is coming.");
        ++invocationCounter;
    }

    public int getInvocationCounter() {
        return invocationCounter;
    }

}
