package com.fobgochod.spring.ioc.environment.placeholder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PlaceholderApplication {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("config/customer-${username}.xml");
        Customer customer = ac.getBean(Customer.class);
        System.out.println("customer = " + customer);
    }
}
