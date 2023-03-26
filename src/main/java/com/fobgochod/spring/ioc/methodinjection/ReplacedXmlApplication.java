package com.fobgochod.spring.ioc.methodinjection;

import com.fobgochod.spring.ioc.methodinjection.replaced.OriginalPojo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReplacedXmlApplication {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("config/replaced-method.xml");

        OriginalPojo dog = ac.getBean(OriginalPojo.class);
        dog.hello();
        dog.hello("woof woof");
    }
}
