package com.fobgochod.spring.ioc.annotation.instantiation.factorymethod;

import org.springframework.stereotype.Component;

@Component
public class FactoryMethodPojoInstanceFactory {

    public FactoryMethodPojo getFactoryMethodPojo(int id, String name) {
        FactoryMethodPojo factoryMethodPojo = new FactoryMethodPojo();
        factoryMethodPojo.setId(id);
        factoryMethodPojo.setName(name);
        return factoryMethodPojo;
    }
}
