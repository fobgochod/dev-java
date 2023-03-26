package com.fobgochod.spring.ioc.annotation.instantiation.supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class SupplierPojoBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition user = beanFactory.getBeanDefinition("supplierPojo");
        GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) user;
        genericBeanDefinition.setInstanceSupplier(SupplierPojoSupplier::createObject);
        genericBeanDefinition.setBeanClass(SupplierPojo.class);
    }
}
