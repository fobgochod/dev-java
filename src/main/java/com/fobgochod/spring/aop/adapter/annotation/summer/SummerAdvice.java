package com.fobgochod.spring.aop.adapter.annotation.summer;

import org.aopalliance.aop.Advice;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

public interface SummerAdvice extends Advice {

    void summer(Method method, Object[] args, @Nullable Object target) throws Throwable;
}
