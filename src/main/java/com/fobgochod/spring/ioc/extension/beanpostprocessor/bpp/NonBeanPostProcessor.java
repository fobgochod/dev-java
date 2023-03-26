package com.fobgochod.spring.ioc.extension.beanpostprocessor.bpp;

import com.fobgochod.spring.ioc.annotation.populatebean.populate.domain.PopulatePojo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class NonBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PopulatePojo) {
            System.out.printf("3. BeanPostProcessor Non Order [%s] BeforeInitialization. \n", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PopulatePojo) {
            System.out.printf("6. BeanPostProcessor Non Order [%s] AfterInitialization. \n", beanName);
        }
        return bean;
    }
}
