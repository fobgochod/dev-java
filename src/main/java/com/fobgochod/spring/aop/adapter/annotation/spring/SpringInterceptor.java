package com.fobgochod.spring.aop.adapter.annotation.spring;

import com.fobgochod.spring.aop.aspect.util.AopLogUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpringInterceptor implements MethodInterceptor {

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation mi) throws Throwable {
        System.out.println("Spring is coming.");
        AopLogUtil.annotationLog(-1, Spring.class, mi.getMethod().getName(), mi.getArguments(), null);
        return mi.proceed();
    }
}
