package com.fobgochod.spring.ioc.additional.tracking;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TrackingApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.fobgochod.spring.ioc.additional.tracking");
    }
}
