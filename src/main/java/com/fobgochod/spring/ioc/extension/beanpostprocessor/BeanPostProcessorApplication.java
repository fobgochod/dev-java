package com.fobgochod.spring.ioc.extension.beanpostprocessor;

import com.fobgochod.spring.ioc.annotation.populatebean.populate.domain.PopulatePojo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.fobgochod.spring.ioc.extension.beanpostprocessor")
public class BeanPostProcessorApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(BeanPostProcessorApplication.class);
        ac.refresh();
    }

    @Bean
    public PopulatePojo populatePojo() {
        return new PopulatePojo();
    }
}
