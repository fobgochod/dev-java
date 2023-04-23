package com.fobgochod.spring.aop.adapter.service;

import com.fobgochod.spring.aop.adapter.annotation.spring.Spring;
import com.fobgochod.spring.aop.aspect.util.AopLogUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JdkCalculator implements Calculator {

    @Spring
    @Override
    public int add(int a, int b) {
        int result = a + b;
        AopLogUtil.printLog("", 3, JdkCalculator.class, "add", new Object[]{a, b}, result);
        return result;
    }

    @Async
    @Override
    public void minus(int a, int b) {
        int c = a - b;
        System.out.println("a - b = " + c);
    }
}
