package com.fobgochod.spring.ioc.extension.beanpostprocessor.bpp;

import com.fobgochod.spring.ioc.annotation.populatebean.populate.domain.PopulatePojo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

@Component
public class PriorityOrderedBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PopulatePojo) {
            System.out.printf("1. BeanPostProcessor PriorityOrdered [%s] BeforeInitialization. \n", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PopulatePojo) {
            System.out.printf("4. BeanPostProcessor PriorityOrdered [%s] AfterInitialization. \n", beanName);
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
