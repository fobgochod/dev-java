package com.fobgochod.spring.aop.aspectj;

import com.fobgochod.spring.aop.aspectj.config.AopConfiguration;
import com.fobgochod.spring.aop.aspectj.service.cglib.CglibCalculator;
import com.fobgochod.spring.aop.aspectj.service.jdk.Calculator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopApplication {

    public static void main(String[] args) throws NoSuchMethodException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AopConfiguration.class);
        ac.refresh();

        System.out.println("JdkDynamicAopProxy=================================================================");

        Calculator jdkCalculator = ac.getBean(Calculator.class);
        jdkCalculator.add(1, 1);

        System.out.println("ObjenesisCglibAopProxy==============================================================");

        CglibCalculator cglibCalculator = ac.getBean(CglibCalculator.class);
        cglibCalculator.add(1, 1);
    }
}
