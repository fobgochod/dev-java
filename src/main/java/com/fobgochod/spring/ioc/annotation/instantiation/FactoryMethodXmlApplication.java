package com.fobgochod.spring.ioc.annotation.instantiation;

import com.fobgochod.spring.ioc.annotation.instantiation.factorymethod.FactoryMethodPojo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryMethodXmlApplication {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("config/factory-method.xml");

        FactoryMethodPojo factoryMethodPojo = ac.getBean("factoryMethodPojo1", FactoryMethodPojo.class);
        System.out.println(factoryMethodPojo);
        FactoryMethodPojo factoryMethodPojo2 = ac.getBean("factoryMethodPojo2", FactoryMethodPojo.class);
        System.out.println(factoryMethodPojo2);
    }
}
