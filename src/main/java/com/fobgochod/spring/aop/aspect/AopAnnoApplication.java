package com.fobgochod.spring.aop.aspect;

import com.fobgochod.spring.aop.aspect.aspectj.AnnoConfiguration;
import com.fobgochod.spring.aop.aspect.service.cglib.CglibCalculator;
import com.fobgochod.spring.aop.aspect.service.jdk.Calculator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopAnnoApplication {

    public static void main(String[] args) throws NoSuchMethodException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AnnoConfiguration.class);
        ac.refresh();

        System.out.println("JdkDynamicAopProxy=================================================================");

        Calculator jdkCalculator = ac.getBean(Calculator.class);
        jdkCalculator.add(1, 1);

        System.out.println("ObjenesisCglibAopProxy==============================================================");

        CglibCalculator cglibCalculator = ac.getBean(CglibCalculator.class);
        cglibCalculator.add(1, 1);
    }
}
