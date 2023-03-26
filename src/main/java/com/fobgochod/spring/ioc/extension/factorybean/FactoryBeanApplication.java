package com.fobgochod.spring.ioc.extension.factorybean;

import com.fobgochod.spring.ioc.extension.factorybean.factory.CustomFactoryBean;
import com.fobgochod.spring.ioc.extension.factorybean.factory.FactoryBeanPojo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.fobgochod.spring.ioc.extension.factorybean.factory")
public class FactoryBeanApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(FactoryBeanApplication.class);
        ac.refresh();

        CustomFactoryBean customFactoryBean = ac.getBean("&customFactoryBean", CustomFactoryBean.class);
        System.out.println("customFactoryBean = " + customFactoryBean);

        FactoryBeanPojo pojo1 = ac.getBean("customFactoryBean", FactoryBeanPojo.class);
        System.out.println(pojo1);
        FactoryBeanPojo pojo2 = ac.getBean("customFactoryBean", FactoryBeanPojo.class);
        System.out.println(pojo2);
    }
}
