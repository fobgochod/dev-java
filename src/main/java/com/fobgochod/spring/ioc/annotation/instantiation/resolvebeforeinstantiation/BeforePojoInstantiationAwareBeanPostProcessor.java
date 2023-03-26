package com.fobgochod.spring.ioc.annotation.instantiation.resolvebeforeinstantiation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

@Component
public class BeforePojoInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanClass == BeforePojo.class) {
            System.out.println("beanName:" + beanName + "----执行postProcessBeforeInstantiation方法");
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanClass);
            enhancer.setCallback(new BeforePojoMethodInterceptor());
            return enhancer.create();
        }
        return null;
    }
}
