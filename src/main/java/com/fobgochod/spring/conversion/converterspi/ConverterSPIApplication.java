package com.fobgochod.spring.conversion.converterspi;

import com.fobgochod.spring.conversion.converterspi.domain.Customer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

import java.util.Collections;

@Configuration
public class ConverterSPIApplication {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext("com.fobgochod.spring.conversion.converterspi");
        System.out.println("customer = " + ac.getBean(Customer.class));
    }

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(Collections.singleton(new AddressConverter()));
        return bean;
    }
}
