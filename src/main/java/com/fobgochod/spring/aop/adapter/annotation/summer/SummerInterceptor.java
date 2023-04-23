package com.fobgochod.spring.aop.adapter.annotation.summer;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

public class SummerInterceptor implements MethodInterceptor, Serializable {

    private final SummerAdvice advice;

    public SummerInterceptor(SummerAdvice advice) {
        Assert.notNull(advice, "Advice must not be null");
        this.advice = advice;
    }

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.advice.summer(mi.getMethod(), mi.getArguments(), mi.getThis());
        return retVal;
    }
}
