package com.fobgochod.spring.ioc.environment.propertysource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PropertyApplication {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext("com.fobgochod.spring.ioc.environment.propertysource");
        System.out.println("propertyPojo = " + ac.getBean(PropertyPojo.class));
    }
}
