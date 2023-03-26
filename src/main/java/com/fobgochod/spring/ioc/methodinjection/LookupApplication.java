package com.fobgochod.spring.ioc.methodinjection;

import com.fobgochod.spring.ioc.methodinjection.lookup.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.fobgochod.spring.ioc.methodinjection.lookup")
public class LookupApplication {

    @Autowired
    private Human human;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(LookupApplication.class);
        ac.refresh();

        LookupApplication app = ac.getBean(LookupApplication.class);
        System.out.println("human eat different fruit = " + app.human.eatFruit());
        System.out.println("human eat different fruit = " + app.human.eatFruit());

        System.out.println("human drive same car = " + app.human.driveCar());
        System.out.println("human drive same car = " + app.human.driveCar());
    }
}
