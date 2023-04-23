package com.fobgochod.spring.aop.adapter.annotation.summer;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.adapter.AdvisorAdapter;

import java.io.Serializable;

public class SummerAdvisorAdapter implements AdvisorAdapter, Serializable {

    @Override
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof SummerAdvice;
    }

    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        SummerAdvice advice = (SummerAdvice) advisor.getAdvice();
        return new SummerInterceptor(advice);
    }
}
