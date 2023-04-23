package com.fobgochod.spring.aop.adapter;

import com.fobgochod.spring.aop.adapter.annotation.spring.EnableSpring;
import com.fobgochod.spring.aop.adapter.service.Calculator;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableSpring
@EnableAspectJAutoProxy
@EnableAsync
@Configuration
@ComponentScan(basePackages = "com.fobgochod.spring.aop.adapter.service")
public class SpringAdapterApplication {

    @Bean
    public Executor taskExecutor() {
        return Executors.newFixedThreadPool(1);
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(SpringAdapterApplication.class);
        ac.refresh();

        Calculator jdkCalculator = ac.getBean(Calculator.class);
        int add = jdkCalculator.add(1, 1);
        System.out.println("add = " + add);

        jdkCalculator.minus(2, 1);
    }
}
