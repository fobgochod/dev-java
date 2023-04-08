package com.fobgochod.spring.aop.aspect.service.jdk;

import com.fobgochod.spring.aop.aspect.util.AopLogUtil;
import org.springframework.stereotype.Service;

@Service
public class JdkCalculator implements Calculator {

    @Override
    public int add(int a, int b) throws NoSuchMethodException {
        int result = a + b;
        AopLogUtil.printLog("", 3, JdkCalculator.class, "add", new Object[]{a, b}, result);
        return result;
    }
}
