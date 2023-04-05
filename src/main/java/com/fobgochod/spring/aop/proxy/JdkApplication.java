package com.fobgochod.spring.aop.proxy;

import com.fobgochod.spring.aop.proxy.jdk.Calculator;
import com.fobgochod.spring.aop.proxy.jdk.CalculatorProxy;
import com.fobgochod.spring.aop.proxy.jdk.StandardCalculator;

public class JdkApplication {

    public static void main(String[] args) {
        // jdk 8
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // jdk17
        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        Calculator calculator = CalculatorProxy.getProxy(new StandardCalculator());
        int add = calculator.add(1, 2);
        System.out.println("add = " + add);
        System.out.println("calculator = " + calculator.getClass());
    }
}
