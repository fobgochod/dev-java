package com.fobgochod.spring.ioc.annotation.instantiation;

import com.fobgochod.spring.ioc.annotation.instantiation.resolvebeforeinstantiation.BeforePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.fobgochod.spring.ioc.annotation.instantiation.resolvebeforeinstantiation")
public class ResolveBeforeInstantiationApplication {

    @Autowired
    private BeforePojo beforePojo;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(ResolveBeforeInstantiationApplication.class);
        ac.refresh();

        ResolveBeforeInstantiationApplication app = ac.getBean(ResolveBeforeInstantiationApplication.class);
        app.beforePojo.doSomeThing();
    }
}
