package com.fobgochod.spring.aop.adapter;

import com.fobgochod.spring.aop.adapter.annotation.summer.AspectJSummerAdvice;
import com.fobgochod.spring.aop.adapter.service.Calculator;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SummerAdapterApplication {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/advisor-adapter.xml");
        Calculator calculator = context.getBean("jdkCalculator", Calculator.class);
        int add = calculator.add(1, 1);
        System.out.println("add = " + add);

        int counter = getAdviceImpl(calculator).getInvocationCounter();
        System.out.println("counter = " + counter);
    }

    private static AspectJSummerAdvice getAdviceImpl(Calculator calculator) {
        Advised advised = (Advised) calculator;
        Advisor advisor = advised.getAdvisors()[0];
        return (AspectJSummerAdvice) advisor.getAdvice();
    }
}
