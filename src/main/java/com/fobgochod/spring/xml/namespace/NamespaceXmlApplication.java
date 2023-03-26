package com.fobgochod.spring.xml.namespace;

import com.fobgochod.spring.xml.namespace.handler.Cat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NamespaceXmlApplication {

    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:config/animal.xml");
        Cat cat = ac.getBean(Cat.class);
        System.out.println("I have a cat: " + cat);
    }
}
