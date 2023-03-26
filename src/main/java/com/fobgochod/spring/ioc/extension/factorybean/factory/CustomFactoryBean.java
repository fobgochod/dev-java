package com.fobgochod.spring.ioc.extension.factorybean.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CustomFactoryBean implements FactoryBean<FactoryBeanPojo> {

    @Override
    public FactoryBeanPojo getObject() throws Exception {
        FactoryBeanPojo factoryBeanPojo = new FactoryBeanPojo();
        factoryBeanPojo.setId(3);
        factoryBeanPojo.setName("zhangsan");
        return factoryBeanPojo;
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBeanPojo.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
