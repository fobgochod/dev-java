package com.fobgochod.spring.ioc.annotation.instantiation;

import com.fobgochod.spring.ioc.annotation.instantiation.prototype.PrototypePojo;
import com.fobgochod.spring.ioc.annotation.instantiation.prototype.SingletonPojo;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.fobgochod.spring.ioc.annotation.instantiation.prototype")
public class PrototypeApplication {

    @Autowired
    private SingletonPojo singletonPojo;
    @Autowired
    private ObjectFactory<SingletonPojo> singletonFactory;

    @Autowired
    private ObjectFactory<PrototypePojo> prototypeFactory;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(PrototypeApplication.class);
        ac.refresh();

        PrototypeApplication app = ac.getBean(PrototypeApplication.class);
        System.out.println("singleton0 = " + app.singletonPojo);
        System.out.println("singleton1 = " + app.singletonFactory.getObject());
        System.out.println("singleton2 = " + app.singletonFactory.getObject());

        System.out.println("prototype1 = " + app.prototypeFactory.getObject());
        System.out.println("prototype2 = " + app.prototypeFactory.getObject());
    }
}
